(ns thinkstats.presidents
  (:use thinkstats.utils)
  (:require [incanter.core :as ic]
            [incanter.io :as io]
            [clj-time.core :as dt]
            [clj-time.format :as ft]))

;; Data format: OB,OD,Name,Date of Birth,Birth Name,Cen,OO,Birthplace,State,AA
(def presidents-uri
  (str (cwd) "/data/presidents.csv"))

(def parse-date (partial ft/parse (ft/formatter "yyyy-mm-dd")))

(defn dataset []
  (let [ds (io/read-dataset presidents-uri :skip 1 :header true)
        dates (->> ds
                   (ic/$ (keyword "Date of Birth"))
                   (map parse-date))]
    (ic/conj-cols ds
                  (ic/dataset [:dob] dates))))
