(ns thinkstats.exercises.ex-4-4
  (:require [incanter.stats :as stats]
            [incanter.core :as ic]
            [incanter.distributions :as dist]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-4-4

;; Choosing the parameters xm = 100 cm and α = 1.7, we get a distribution with
;; a reasonable minimum, 100 cm, and median, 150 cm.
;; Generate 6 billion random values from this distribution.

;; What is the mean of this sample?
;; What fraction of the population is shorter than the mean?
;; How tall is the tallest person in Pareto World?

(defn paretovariate [alpha xm]
  (let [uni (dist/uniform-distribution 0 1)
        inv (fn [u] (/ xm (ic/pow u (/ 1.0 alpha))))]
    (map inv (repeatedly #(.draw uni)))))

(defn ex-4-4 []
  (let [n 6000 ;6000000
        sample (->> (paretovariate 1.7 100)
                    (take n)
                    (vec))
        mean (stats/mean sample)
        shorter (count (filter #(< % mean) sample))
        fraction (* 100.0 (/ shorter n))
        tallest (apply max sample)]
    (println (str (format "Mean: %.0fcm\n" mean)
                  (format "Shorter than average: %.0f%%\n" fraction)
                  (format "Tallest: %.0fcm\n" tallest)))))

(defn -main []
  (ex-4-4)
  nil)
