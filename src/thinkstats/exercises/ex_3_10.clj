(ns thinkstats.exercises.ex-3-10
  (:use incanter.core
        [thinkstats.stats :only [cdf value]])
  (:require [thinkstats.survey :as survey]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-10

(defn percentile [cdf rank]
  (value cdf (/ rank 100.0)))

;; Write a function called Median that takes a Cdf and computes the median...

(defn median [cdf]
  (percentile cdf 50))

;; ...and one called Interquartile that computes the interquartile range.

(defn interquartile [cdf]
  (- (percentile cdf 75) (percentile cdf 25)))

(defn ex-3-10 []
  ;; Compute the 25th, 50th, and 75th percentiles of the birth weight CDF
  (let [weights (cdf (survey/weights (survey/dataset)))]
    (do
      (print (str (format "Median: %f\n" (median weights))
                  (format "Interquartile: %f\n" (interquartile weights))
                  (format "Percentiles (25,50,75): %f, %f, %f\n"
                          (percentile weights 25)
                          (percentile weights 50)
                          (percentile weights 75)))))))

;; ===========================================================================

(defn -main []
  (ex-3-10))
