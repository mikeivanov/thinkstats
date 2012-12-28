(ns thinkstats.exercises.ex-3-2
  (:use incanter.core
	[thinkstats.relay :only (race-dataset)]
        [thinkstats.stats  :only (maphash pmf normalize)])
  (:require [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-2

;; 3.2 ----------------------------------------------------------------------
;; Write a function called BiasPmf that takes a Pmf representing the actual
;; distribution of runners’ speeds, and the speed of a running observer,
;; and returns a new Pmf representing the distribution of runners’
;; speeds as seen by the observer.

(defn bias-pmf [bias-fn pmf]
  (->> pmf
       (maphash (fn [[x p]] [x (bias-fn x p)]))
       (normalize)))

;; Compute the distribution of speeds you would observe if you ran a
;; relay race at 7.5 MPH with this group of runners.

(def observer-speed 7.5)

(defn observer-bias [speed prob]
  (abs ($= prob * (speed - observer-speed))))

(defn ex-3-2 []
  (let [speeds ($ :speed (race-dataset))
        actual (pmf speeds)
        observed (bias-pmf observer-bias actual)]
    (-> (charts/xy-plot (keys actual)
                        (vals actual)
                        :series-label "Actual"
                        :legend true
                        :title "Runner Speeds"
                        :x-label "Speed, MPH"
                        :y-label "Probability")
        (charts/add-lines (keys observed)
                          (vals observed)
                          :series-label "Observed at 7.5 MPH")
        (view))))

;; ===========================================================================

(defn -main []
  (ex-3-2))

