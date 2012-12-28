(ns thinkstats.exercises.ex-2-7
  (:use [thinkstats.survey :only (pregnancy-lengths-split)]
        [thinkstats.stats  :only (normalize sum)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-7

;; 2.7 ----------------------------------------------------------------------
;; "baby is early if it is born during Week 37 or earlier, on time if it is
;; born during Week 38, 39 or 40, and late if it is born during Week 41 or
;; later."

(def pmf (comp normalize frequencies))

;; Write functions named ProbEarly, ProbOnTime and ProbLate that take a PMF
;; and compute the fraction of births that fall into each bin

(defn within? [lower upper x]
  (and (<= lower x) (< x upper)))

(defn prob-range [lower upper pmf]
  (sum (for [[t,p] pmf :when (within? lower upper t)] p)))

(def prob-early   (partial prob-range 25 38))
(def prob-on-time (partial prob-range 38 41))
(def prob-late    (partial prob-range 41 46))

;; compute the probability of being born early,
;; on time, or late. One for first babies, one for others, and one
;; for all live births.

(defn bucket-probs [t]
  (map #(% (pmf t))
       [prob-early prob-on-time prob-late]))

(defn ex-2-7-1 []
  (println "Probabilties:")
  (let [[fb ob] (pregnancy-lengths-split)
        all     (concat fb ob)]
    (loop [groups [[fb  "First"]
                   [ob  "Others"]
                   [all "All"]]]
      (if-let [[group label] (first groups)]
        (let [[pe pn pl] (bucket-probs group)]
          (do
            (println (format (str "  %s:\n"
                                  "    early=%f\n"
                                  "    on time=%f\n"
                                  "    late=%f")
                             label pe pn pl))
            (recur (rest groups))))))))

;; Write code to compute the relative risks of being born on
;; time and being late.

(defn ex-2-7-2 []
  (let [[fb ob]    (pregnancy-lengths-split)
        [re rn rl] (map / (bucket-probs fb) (bucket-probs ob))]
    (println (format (str "Relative (first/other) risks of being born:\n"
                          "  early=%f\n"
                          "  on time=%f\n"
                          "  late=%f")
                     re rn rl))))

;; ===========================================================================

(defn -main []
  (ex-2-7-1)
  (ex-2-7-2))

