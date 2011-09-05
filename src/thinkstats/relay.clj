(ns thinkstats.relay
  (:use incanter.core
        [clojure.contrib.duck-streams :only (read-lines)]))

;; Data format:
;; Place Div/Tot  Div   Guntime Nettime  Pace  Name ...
;; ===== ======== ===== ======= =======  ===== ====================== ...
;;     1   1/362  M2039   30:43   30:42   4:57 Brian Harvey           ...
;;     2   2/362  M2039   31:36   31:36   5:06 Mark Miller            ...

(def record-rx
  #"(?x)^
    \s+\d+\s+           # Place
    (?:\d+/\d+\s+)?     # Div/Tot
    (?:\w\d{4}\s+)?     # Div
    (?:\d\d:\d\d\s+){2} # Guntime and Nettime
    (\d+):(\d\d)\s+     # Pace <= this is what we are after
    ")

(defn- parse-record [line]
  (if-let [[_ mstr sstr] (re-find record-rx line)]
    (let [[min sec] (map #(Integer/parseInt %) [mstr sstr])
          pace      ($= sec + min * 60.0)]
      (double pace))))

(defn- pace-to-speed [pace]
  ($= 60.0 * 60.0 / pace))

(def *run-results-url*
  "http://coolrunning.com/results/10/ma/Apr25_27thAn_set1.shtml")

(defn speeds []
  (->> *run-results-url*
       get-input-reader
       read-lines
       (keep parse-record)
       (map pace-to-speed)))

