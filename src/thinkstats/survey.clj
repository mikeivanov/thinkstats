(ns thinkstats.survey
  (:use clojure.contrib.math
	[clojure.contrib.duck-streams :only (read-lines)])
  (:import (java.io FileInputStream)
	   (java.util.zip GZIPInputStream)))

(defmulti parse-field (fn [s type] type))

(defmethod parse-field :int [s _]
  (try
    (Integer/parseInt (.trim s))
    (catch NumberFormatException _
      :NA)))

(defmethod parse-field :float [s _]
  (try
    (Float/parseFloat (.trim s))
    (catch NumberFormatException _
      :NA)))

(defn parse-record [fields line]
  (apply merge
         (for [[key start end type] fields]
           (try
             (let [sub (subs line (dec start) end)
                   val (parse-field sub type)]
               {key val})
             (catch Exception ex
               (throw (Exception.
                       (format (str "Error parsing (%d:%d) "
                                    "as %s in '%s'")
                               start end (str type) line)
                       ex)))))))

(defn parse-table [fields lines]
  (lazy-seq
   (when-let [line (first lines)]
     (cons (parse-record fields line)
	   (parse-table fields (rest lines))))))

(def preg-fields '((:caseid,        1,  12, :int)
		   (:nbrnaliv,     22,  22, :int)
		   (:babysex,      56,  56, :int)
		   (:birthwgt_lb,  57,  58, :int)
		   (:birthwgt_oz,  59,  60, :int)
		   (:prglength,   275, 276, :int)
		   (:outcome,     277, 277, :int)
		   (:birthord,    278, 279, :int)
		   (:agepreg,     284, 287, :int)
		   (:finalwgt,    423, 440, :float)))

(defn open-resource [name]
  (let [rsc (str "thinkstats/survey/" name)
        thr (Thread/currentThread)
        ldr (.getContextClassLoader thr)]
    (.getResourceAsStream ldr rsc)))

(defn read-resource-table [fields name]
  (parse-table fields
               (-> name
                   open-resource
                   GZIPInputStream.
                   read-lines)))

(defn alive? [rec]
  (= 1 (:outcome rec)))

(defn firstborn? [rec]
  (= 1 (:birthord rec)))

(defn lengths [records]
  (map :prglength records))

(defmulti dataset (fn [& args] (or (first args) :all)))

(defmethod dataset :all [& _]
  (read-resource-table preg-fields
                       "2002FemPreg.dat.gz"))

(defmethod dataset :alive [& _]
  (filter alive? (dataset :all)))

(defn with-reasonable-preg-length? [rec]
  (let [x (:prglength rec)]
    (and (< 25 x) (<= x 45))))

(defn pregnancy-lengths []
  (lengths (filter with-reasonable-preg-length? (dataset :alive))))

(defn pregnancy-lengths-partitioned []
  (let [records    (filter with-reasonable-preg-length? (dataset :alive))
        firstborns (group-by firstborn? records)
        fb         (lengths (firstborns true))
        ob         (lengths (firstborns false))]
    [fb ob]))

