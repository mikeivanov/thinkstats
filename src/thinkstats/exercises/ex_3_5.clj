(ns thinkstats.exercises.ex-3-5
  (:use incanter.core
        [thinkstats.relay :only (speeds)]
        [thinkstats.stats :only (maphash)])
  (:require [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-5

;; 3.5 ----------------------------------------------------------------------
;; Generate a plot that shows the CDF of running speeds.

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

(defn make-cdf [histogram]
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

(defn ex-3-5 []
  (let [data (speeds)
        cdf  (cdf data)
        ds   (dataset ["speed" "cdf"] (items cdf))]
    (with-data ds
      (view (charts/xy-plot :speed :cdf)))))

;; ===========================================================================

(defn -main []
  (ex-3-5))

