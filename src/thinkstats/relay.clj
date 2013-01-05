(ns thinkstats.relay
  (:use incanter.core
        thinkstats.utils
        [clojure.string :only [trim]]))

;; Data format:
;; Place Div/Tot  Div   Guntime Nettime  Pace  Name ...
;; ===== ======== ===== ======= =======  ===== ====================== ...
;;     1   1/362  M2039   30:43   30:42   4:57 Brian Harvey           ...
;;     2   2/362  M2039   31:36   31:36   5:06 Mark Miller            ...

(defn- parse-time [time]
  (if-let [min-sec  (re-find #"(\d+):(\d+)" time)]
    (let [[min sec] (map parse-int (drop 1 min-sec))
          seconds   ($= sec + min * 60.0)]
      (double seconds))))

(defn- pace-to-speed [pace]
  ($= 60.0 * 60.0 / pace))

(defn- re-group-keys [rx]
  (map (comp keyword second)
       (re-seq #"\(\?<([^>]+)>" (str rx))))

(defn- scrape
  ([rx lines]
     (scrape rx (re-group-keys rx) lines))
  ([rx columns lines]
     (dataset columns
              (->> lines
                   (map (partial re-find rx))
                   (filter (complement nil?))
                   (map rest)))))

(def race-rx #"(?x)^\s+
               (\d+)\s+             # (?<place>)
               (?:(\d+)/(\d+))?\s+  # (?<divpos>) (?<divtot>)
               (?:([MF]\d{4}))?\s+  # (?<division>)
               (\d+:\d+)\s+         # (?<guntime>)
               (\d+:\d+)\s+         # (?<nettime>)
               (\d+:\d+)\s+         # (?<pace>)
               (.*?)\s+             # (?<name>)
               (?:(\d+))?\s+        # (?<age>)
               (?:(M|F))?\s+        # (?<sex>)
               (\d+)\s+             # (?<racenum>)
               (\w.+?)\s([A-Z]{2})  # (?<city>) (?<state>)
               \s*$")

(def ^:dynamic *run-results-url*
  ;; http://coolrunning.com/results/10/ma/Apr25_27thAn_set1.shtml
  (str (cwd) "/book/workspace.thinkstats/ThinkStats/Apr25_27thAn_set1.shtml"))

(defn race-dataset [& [url]]
  (let [url (or url *run-results-url*)
        ds  (->> url
                 (read-lines)
                 (scrape race-rx))
        speeds ($map (comp pace-to-speed parse-time) :pace ds)]
    (conj-cols ds
               (dataset [:speed] speeds))))

