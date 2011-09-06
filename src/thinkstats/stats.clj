(ns thinkstats.stats
  (:use clojure.contrib.generic.math-functions))

(def sum (partial reduce +))

(defn mean [t]
  (/ (sum t)
     (count t)))

(defn variance [t & [mu]]
  (let [mu   (or mu (mean t))
        dev2 (map #(sqr (- % mu)) t)
        var  (mean dev2)]
    [mu var]))

(def maphash (comp (partial into {}) map))

(defn normalize [hist]
  (let [total (double (sum (vals hist)))]
    (maphash (fn [[x f]] [x (/ f total)])
             hist)))

;; pmf -----------------------------------------------------------------

(def pmf (comp normalize frequencies))

(defn pmf-mean [pmf]
  (->> pmf
       (map (fn [[x p]] (* x p)))
       (sum)))

(defn pmf-variance [pmf & [mu]]
  (let [mu  (or mu (pmf-mean pmf))
        var (->> pmf
                 (map (fn [[x p]] (* p (sqr (- x mu)))))
                 (sum))]
    [mu var]))

;; cdf -----------------------------------------------------------------

(defn bisect
  ([t x] (bisect t x 0 (dec (count t))))
  ([t x left right]
     (loop [left left, right right]
       (if (< left right)
         (let [mid (quot (+ left right) 2)]
           (if (< x (get t mid))
             (recur left mid)
             (recur (inc mid) right)))
         left))))

(defprotocol CDFOps
  (probability [this x])
  (value [this p])
  (items [this]))

(defrecord CDF [values probabilities]
  CDFOps
  
  (probability [this x]
    (cond (< x (first values)) 0.0
          (> x (last values)) 1.0
          :else (let [index (bisect values x)]
                  (get probabilities (dec index)))))
  
  (value [this p]
    (assert (and (<= 0.0 p)
                 (<= p 1.0)))
    (cond (= p 0) (first values)
          (= p 1) (last values)
          :else   (let [index (bisect probabilities p)]
                    (if (= p (last probabilities))
                      (get values (dec index))
                      (get values index)))))

  (items [this]
    (map vector values probabilities)))

(defn- make-cdf [histogram]
  (let [size (count histogram)]
    (loop [acc 0
           xf  (sort-by #(first %) histogram)
           xs  []
           fs  []]
      (if-let [[x f] (first xf)]
        (let [acc (+ acc f)]
          (recur acc
                 (rest xf)
                 (conj xs x)
                 (conj fs acc)))
        (CDF. xs
              (vec (map #(double (/ % acc)) fs)))))))

(defprotocol MakeCDF
  (cdf [something]))

(extend-type clojure.lang.Sequential
  MakeCDF
  (cdf [sequential]
    (make-cdf (frequencies sequential))))

(extend-type clojure.lang.MapEquivalence
  MakeCDF
  (cdf [histogram]
    (make-cdf histogram)))

