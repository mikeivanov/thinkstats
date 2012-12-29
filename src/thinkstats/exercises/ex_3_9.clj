(ns thinkstats.exercises.ex-3-9
  (:use incanter.core
        [thinkstats.stats :only [cdf value]])
  (:require [thinkstats.survey :as survey]
            [incanter.core :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-9

;; 3.9 ----------------------------------------------------------------------

;; Write a function called Sample, that takes a Cdf and an integer, n, and
;; returns a list of n values chosen at random from the Cdf.

(defn sample [cdf n]
  (for [i (range n) :let [r (rand)]]
    (value cdf r)))

(defn ex-3-9 []
  ;; Using the distribution of birth weights from the NSFG
  ;; generate a random sample with 1000 elements.
  (let [weights   (cdf (survey/weights (survey/dataset)))
        resampled (cdf (sample weights 1000))]
    (-> (charts/xy-plot (:values weights)
                        (:probabilities weights)
                        :series-label "Original"
                        :legend true
                        :title "Weights distribution"
                        :x-label "Weight"
                        :y-label "Probability")
      (charts/add-lines (:values resampled)
                        (:probabilities resampled)
                        :series-label "Resampled")
      (ic/view))))

;; ===========================================================================

(defn -main []
  (ex-3-9))
