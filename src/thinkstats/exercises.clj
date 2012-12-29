(ns thinkstats.exercises
  (use [clojure.string :only [replace]]))

(defn call [name & args]
    (when-let [fun (ns-resolve *ns* (symbol name))]
      (apply fun args)))

(defn ex [id]
  (let [path (str "src/thinkstats/exercises/" (replace (str id) #"-" "_") ".clj")
        name (str "thinkstats.exercises." id "/-main")]
    (do
      (load-file path)
      (println (str "--------> " id))
      (call name))))

(defn -main []
  (ex 'ex-1-3)
  (ex 'ex-2-1)
  (ex 'ex-2-3)
  (ex 'ex-histogram)
  (ex 'ex-2-4)
  (ex 'ex-2-5)
  (ex 'ex-2-6)
  (ex 'ex-2-7)
  (ex 'ex-2-8)
  (ex 'ex-3-1)
  (ex 'ex-3-2)
  (ex 'ex-3-3)
  (ex 'ex-3-4)
  (ex 'ex-3-5)
  (ex 'ex-3-6)
  (ex 'ex-3-7)
  (ex 'ex-3-8)
  (ex 'ex-3-9))
