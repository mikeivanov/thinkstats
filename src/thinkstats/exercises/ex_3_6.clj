(ns thinkstats.exercises.ex-3-6
  (:use incanter.core
        [thinkstats.stats :only (cdf probability)])
  (:require [thinkstats.survey :as survey]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-6

;; 3.6 ----------------------------------------------------------------------
;; Using the pooled data (all live births), compute the distribution of birth
;; weights and use it to find your percentile rank. If you were a first baby,
;; find your percentile rank in the distribution for first babies.
;; How big is the difference between your percentile ranks in the two
;; distributions?

(defn percentile-rank [t x]
  (* 100.0 (probability (cdf t) x)))

(defn weight-percentile-rank [recs weight]
  (percentile-rank (survey/weights recs) weight))
  
(def my-weight 3.950)

(defn ex-3-6 []
  (let [pool      (survey/dataset :alive)
        rank-pool (weight-percentile-rank pool my-weight)
        rank-fb   (weight-percentile-rank (filter survey/firstborn? pool)
                                          my-weight)]
    (println (format (str "My percentile rank=%f, "
                          "among firstborns=%f, "
                          "difference=%f")
                     rank-pool
                     rank-fb
                     (abs (- rank-pool rank-fb))))))

;; ===========================================================================

(defn -main []
  (ex-3-6))

