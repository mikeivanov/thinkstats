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

(defn weight-kg [rec]
  ($= (:birthwgt_lb rec) * 0.45359237 +
      (:birthwgt_oz rec) * 0.0283495231))

(defn weights [recs]
  (->> recs
       (map weight-kg)
       (filter (complement nil?))
       (filter #(<= 3.0 % 5.0))))

(def *my-weight* 3.950)

(defn percentile-rank [t x]
  (* 100.0 (probability (cdf t) x)))

(defn ex-3-6 []
  (let [pool      (survey/dataset :alive)
        rank-pool (percentile-rank (weights pool)
                                   *my-weight*)
        rank-fb   (percentile-rank (weights (filter survey/firstborn? pool))
                                   *my-weight*)]
    (println (format (str "My percentile rank=%f, "
                          "among firstborns=%f, "
                          "difference=%f")
                     rank-pool
                     rank-fb
                     (abs (- rank-pool rank-fb))))))

;; ===========================================================================

(defn -main []
  (ex-3-6))

