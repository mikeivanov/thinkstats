(ns thinkstats.exercises.ex-3-2
  (:use clojure.contrib.generic.math-functions
	[clojure.contrib.duck-streams :only (read-lines)]
        [thinkstats.stats  :only (maphash pmf normalize)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-2

;; 3.2 ----------------------------------------------------------------------
;; Write a function called BiasPmf that takes a Pmf representing the actual
;; distribution of runners’ speeds, and the speed of a running observer,
;; and returns a new Pmf representing the distribution of runners’
;; speeds as seen by the observer.

(def *run-results-url*
  "http://coolrunning.com/results/10/ma/Apr25_27thAn_set1.shtml")

(defn fetch-url [url]
  (read-lines (java.net.URL. url)))

;; Data format:
;; Place Div/Tot  Div   Guntime Nettime  Pace  Name ...
;; ===== ======== ===== ======= =======  ===== ====================== ...
;;     1   1/362  M2039   30:43   30:42   4:57 Brian Harvey           ...
;;     2   2/362  M2039   31:36   31:36   5:06 Mark Miller            ...

(def *record-rx*
  #"(?x)^
    \s+\d+\s+           # Place
    (?:\d+/\d+\s+)?     # Div/Tot
    (?:\w\d{4}\s+)?     # Div
    (?:\d\d:\d\d\s+){2} # Guntime and Nettime
    (\d+):(\d\d)\s+     # Pace <= this is what we are after
    ")

(defn parse-record [line]
  (if-let [[_ mstr sstr] (re-find *record-rx* line)]
    (let [[min sec] (map #(Integer/parseInt %) [mstr sstr])
          pace      (+ (* 60 min) sec)]
      (double pace))))

(defn pace-to-speed [pace]
  (/ (* 60.0 60.0) pace))

(defn speeds []
  (->> *run-results-url*
       (fetch-url)
       (keep parse-record)
       (map pace-to-speed)))

;; compute the distribution of speeds you would observe if you ran a
;; relay race at 7.5 MPH with this group of runners.

(def *observer-speed* 7.5)

(defn observer-bias [speed prob]
  (* prob (abs (- speed *observer-speed*))))

(defn pmf-bias [bias-fn pmf]
  (->> pmf
       (maphash (fn [[x p]] [x (bias-fn x p)]))
       (normalize)))

(defn ex-3-2 []
  (let [actual   (pmf (speeds))
        observed (pmf-bias observer-bias actual)]
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
        (ic/view))))

;; ===========================================================================

(defn -main []
  (ex-3-2))

