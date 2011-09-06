(ns thinkstats.exercises.ex-3-7
  (:use incanter.core
        [thinkstats.stats :only (cdf probability)])
  (:require [thinkstats.survey :as survey]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-7

;; 3.7 ----------------------------------------------------------------------
;; Suppose you and your classmates compute the percentile rank of your birth
;; weights and then compute the CDF of the percentile ranks. What do you
;; expect it to look like?

(defn percentile-rank [cdf x]
  (* 100.0 (probability cdf x)))

;; it becomes more interesting as the sample size increases
(def *sample-size* 20)

(defn ex-3-7 []
  (let [weights    (survey/weights (survey/dataset :alive))
        weight-cdf (cdf weights)
        sample     (take *sample-size* weights)
        ranks      (map #(percentile-rank weight-cdf %) sample)
        rank-cdf   (cdf ranks)]
    (view (charts/xy-plot (:values rank-cdf)
                          (:probabilities rank-cdf)))))

;; ===========================================================================

(defn -main []
  (ex-3-7))

