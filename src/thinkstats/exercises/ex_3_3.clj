(ns thinkstats.exercises.ex-3-3
  (:use clojure.contrib.generic.math-functions))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-3

;; 3.3 ----------------------------------------------------------------------
;; Use the percentile rank to compute the index of the corresponding
;; percentile.

(def data [55 66 77 88 99])

(defn percentile [scores percentile-rank]
  (let [rank   (/ percentile-rank 100.0)
        topidx (- (count scores) 1)
        index  (round (* rank topidx))]
    (nth (sort scores) index)))

(defn ex-3-3 []
  (doseq [pr (range 10 100 10)]
    (println (format "percentile(%d) = %d" pr (percentile data pr)))))

;; ===========================================================================

(defn -main []
  (ex-3-3))

