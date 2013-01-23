(ns thinkstats.exercises.ex-4-3
  (:use thinkstats.utils
        [thinkstats.charts :only [log-scale-y log-scale-x]])
  (:require [thinkstats.stats :as stats]
            [incanter.core :as ic]
            [incanter.charts :as charts]
            [incanter.distributions :as dist]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-4-3

;; Write a wrapper function named paretovariate that takes α and xm as parameters

(defn uniform [a b]
  (let [uni (dist/uniform-distribution a b)]
    (repeatedly #(.draw uni))))

(defn pareto-inv [alpha xm u]
  (/ xm (ic/pow u (/ 1.0 alpha))))

(defn paretovariate [alpha xm]
  (let [uni (dist/uniform-distribution 0 1)]
    (map (partial pareto-inv alpha xm)
         (repeatedly #(.draw uni)))))

(defn plot [[x y]]
  (charts/xy-plot x y
                  :legend false
                  :title "CDF"))

(defn ex-4-3 []
  (->> (paretovariate 1.0 0.5)
       (take 1000)
       (stats/cdf)
       (stats/complementary)
       (stats/components)
       (map drop-last)
       (plot)
       (log-scale-x)
       (log-scale-y)
       (ic/view)))

(defn -main []
  (ex-4-3)
  nil)
