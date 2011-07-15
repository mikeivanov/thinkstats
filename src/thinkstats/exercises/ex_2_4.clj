(ns thinkstats.exercises.ex-2-4
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (pregnancy-lengths)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-4

;; 2.4 ----------------------------------------------------------------------
;; Write a function called RemainingLifetime that takes a Pmf of lifetimes
;; and an age, and returns a new Pmf that represents the distribution of
;; remaining lifetimes.

(def maphash (comp (partial into {}) map))

(defn normalize [hist]
  (let [total (reduce + (vals hist))]
    (maphash (fn [[x f]] [x (/ f total)]) hist)))

(defn remaining-lifetimes [lifetimes t0]
  (normalize (maphash (fn [t] [(- t t0) (lifetimes t)])
                      (filter #(<= t0 %) (keys lifetimes)))))

(defn ex-2-4 []
  (let [lengths   (map float (pregnancy-lengths))
        hist      (frequencies lengths)
        lifetimes (normalize hist)]
    (loop [T [30 33 36 39 42] plot nil]
      (if (seq T)
        (let [t0   (first T)
              future (remaining-lifetimes lifetimes t0)
              t      (keys future)
              p      (vals future)
              plot   (if plot
                       (charts/add-lines plot t p :series-label t0)
                       (charts/xy-plot t p :legend true :series-label t0))]
          (recur (rest T) plot))
        (ic/view plot)))))

;; ===========================================================================

(defn -main []
  (ex-2-4))

