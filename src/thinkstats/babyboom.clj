(ns thinkstats.babyboom
  (:use thinkstats.utils)
  (:require [incanter.core :as ic]))

;; Data format:
;; .....
;; START DATA:
;;     0005       1    3837       5
;;     0104       1    3334      64
;;     0118       2    3554      78
;; .....

(def babyboom-url
  (str (cwd) "/book/workspace.thinkstats/ThinkStats/babyboom.dat"))

(defn- parse-line [line]
  (let [[_ hh mm sex weight minutes] (re-find #"\s*(\d\d)(\d\d)\s+(\d)\s+(\d+)\s+(\d+)\s*" line)]
    (vector (parse-int hh)
            (parse-int mm)
            (parse-int sex)
            (/ (parse-int weight) 1000.0)
            (parse-int minutes))))

(defn dataset [& [url]]
  (->> (or url babyboom-url)
       (read-lines)
       (drop-while #(not (re-matches #"START DATA:\s*" %)))
       (drop 1)
       (map parse-line)
       (ic/dataset [:hh :mm :sex :weight :minutes])))

(defn intervals [ds]
  (->> ds
       (ic/$ :minutes)
       (reverse)
       (pairwise)
       (map (partial apply -))))
