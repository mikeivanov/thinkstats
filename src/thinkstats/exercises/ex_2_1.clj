(ns thinkstats.exercises.ex-2-1
  (:use thinkstats.stats
        clojure.contrib.generic.math-functions
        [thinkstats.survey :only (read-pregnancies-table)]
        [thinkstats.exercises.ex-1-3 :only (partition-firstborns
                                            pregnancy-lengths)]))

;; Run it as:
;;     lein run -m thinkstats.exercises.ex-2-1

;; data ----------------------------------------------------------------------

(def pumpkins [1. 1. 1. ; three decorative pumpkins that are 1 pound each,
               3. 3.    ; two pie pumpkins that are 3 pounds each,
               591.])   ; and one Atlantic Giant(R) pumpkin

;; 2.1.1 ---------------------------------------------------------------------
;; compute the mean, variance and standard deviation of the pumpkins weights

(defn ex-2-1-1 [t]
  (let [[mu var] (variance t)
        stdev    (sqrt var)]
    (println (format (str "Pumpkin weights:\n"
                          "  mean=%f\n"
                          "  variance=%f\n"
                          "  standard deviation=%f")
                     mu var stdev))))

;; 2.1.2 ---------------------------------------------------------------------
;; compute the standard deviation of gestation time for first babies and others

(defn gestation-stdev [records]
  (let [[mu var] (variance (pregnancy-lengths records))
        stdev    (sqrt var)]
    stdev))

(defn ex-2-1-2 []
  (let [pregnancies   (read-pregnancies-table)
        [f-dev o-dev] (map gestation-stdev
                           (partition-firstborns pregnancies))
        diff          (abs (- f-dev o-dev))]
    (println (format (str "Standard deviations of gestation time:\n"
                          "   firstborns=%f\n"
                          "   others=%f\n"
                          "   difference=%f")
                     f-dev
                     o-dev
                     diff))))

;; ===========================================================================

(defn -main []
  (ex-2-1-1 pumpkins)
  (ex-2-1-2))

