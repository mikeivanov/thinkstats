(ns thinkstats.exercises.ex-3-8
  (:use incanter.core
        [thinkstats.relay :only [race-dataset]]
        [thinkstats.stats :only [cdf probability percentile-rank]]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-8

;; 3.8 ----------------------------------------------------------------------

(defn- percentile-rank-in-group [group speed]
  (let [group-cdf (->> group
                       ($ :speed)
                       (cdf))]
    (percentile-rank group-cdf speed)))

;; a) I came in 97th in a field of 1633, so what is my percentile
;; rank in the field?

(defn- percentile-rank-in-field [ds speed]
  (let [rank (percentile-rank-in-group ds speed)]
    (println (format "Percentile rank in field is %f" rank))))

;; b) What is my percentile rank in my division?

(defn- percentile-rank-in-division [ds speed]
  (let [division ($where {:division "M4049"} ds)
        rank     (percentile-rank-in-group division speed)]
    (println (format "Percentile rank in division M4049 is %f" rank))))

(defn ex-3-8 []
  (let [ds (race-dataset)
        speed (->> ds
                   ($where {:name "Allen Downey"})
                   ($ :speed))]
    (percentile-rank-in-field ds speed)
    (percentile-rank-in-division ds speed)))

;; ===========================================================================

(defn -main []
  (ex-3-8))

