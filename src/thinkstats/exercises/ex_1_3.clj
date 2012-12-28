(ns thinkstats.exercises.ex-1-3
  (:use [thinkstats.survey :only (dataset)])
  (:require [clojure.math.numeric-tower :as math]))

;; Run it as:
;;     lein run -m thinkstats.exercises.ex-1-3

;; 1.3.1 ---------------------------------------------------------------------
;; Count records in the pregnancies table.
;; The result should be 13593 pregnancies.

(defn ex-1-3-1 [records]
  (println (format "Number of pregnancies=%d" (count records))))

;; 1.3.2 ---------------------------------------------------------------------
;; Write a loop that iterates table and counts the number of live births.

(defn alive? [rec]
  (= 1 (:outcome rec)))

(defn ex-1-3-2 [records]
  (let [alive (filter alive? records)]
    (println (format "Number of live births=%d" (count alive)))))

;; 1.3.3 ---------------------------------------------------------------------
;; Modify the loop to partition the live birth records into two groups,
;; one for first babies and one for the others.

(defn firstborn? [rec]
  (= 1 (:birthord rec)))

(defn split-firstborns [records]
  (->> records
       (sort-by firstborn?)
       (reverse)
       (split-with firstborn?)))

(defn ex-1-3-3 [records]
  (let [[firstborns others] (split-firstborns (filter alive? records))]
    (println (format "Number of firstborns=%d, other babies=%d"
                     (count firstborns)
                     (count others)))))

;; 1.3.4 ---------------------------------------------------------------------
;; Compute the average pregnancy length (in weeks)
;; for first babies and others. How big is the difference?

(defn pregnancy-lengths [records]
  (->> records
       (map :prglength)
       (map double)))

(defn avg [t]
  (/ (reduce + t) (count t)))

(defn ex-1-3-4 [records]
  (let [[firstborns others] (split-firstborns (filter alive? records))
        mu1                 (avg (pregnancy-lengths firstborns))
        mu2                 (avg (pregnancy-lengths others))
        diff-weeks          (math/abs (- mu1 mu2))
        diff-hours          (* diff-weeks 24 7)]
    (println (format (str "Average pregnancy length\n"
                          "    for firstborns=%f weeks\n"
                          "    other babies=%f weeks\n"
                          "    difference=%f hours")
                     mu1
                     mu2
                     diff-hours))))

;; ==========================================================================

(defn -main []
  (let [pregnancies (dataset)]
    (ex-1-3-1 pregnancies)
    (ex-1-3-2 pregnancies)
    (ex-1-3-3 pregnancies)
    (ex-1-3-4 pregnancies)))

