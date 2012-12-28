(ns thinkstats.exercises.ex-2-3
  (:use thinkstats.stats
        [thinkstats.survey :only (dataset lengths)]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-3


;; 2.3 ----------------------------------------------------------------------
;; Write a function called Mode that takes a Hist object and returns the most
;; frequent value. Write a function called AllModes that takes a Hist object
;; and returns a list of value-frequency pairs in descending order of
;; frequency.

(defn mode [hist]
  (apply max-key hist (keys hist)))

(defn all-modes [hist]
  (reverse (sort-by (fn [[_ f]] f) hist)))

(defn ex-2-3 []
  (let [data  (lengths (dataset :alive))
        hist  (frequencies data)
        modes (all-modes hist)]
    (println (format "Mode=%f, all modes=%s"
                     (mode hist)
                     (apply str (interpose ", " (map str modes)))))))

;; ===========================================================================

(defn -main []
  (ex-2-3))


