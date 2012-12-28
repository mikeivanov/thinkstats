(ns thinkstats.exercises.ex-3-4
  (:require [clojure.math.numeric-tower :as math]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-4

;; 3.4 ----------------------------------------------------------------------
;; Write an implementation of the selection algorithm and use it to write an
;; efficient version of Percentile.

(def data [55 66 77 88 99])

(defn aswap [arr i j]
  (let [t (aget arr i)]
    (do
      (aset arr i (aget arr j))
      (aset arr j t))))

;; in-place quicksort partition
(defn- qs-partition [arr left right pivot]
  (let [val (aget arr pivot)]
    (do
      (aswap arr pivot right)
      (with-local-vars [store left]
        (doseq [i (range left right)]
          (when (<= (aget arr i) val)
            (aswap arr (var-get store) i)
            (var-set store (inc (var-get store)))))
        (aswap arr right (var-get store))
        (var-get store)))))

;; quicksort select
(defn- qs-select [arr left right k]
  (let [midpoint (int (/ (+ left right) 2))
        pivot    (qs-partition arr left right midpoint)]
    (cond (= k pivot) (aget arr k)
          (< k pivot) (qs-select arr left (dec pivot) k)
          :else       (qs-select arr (inc pivot) right k))))

(defn select-kth-smallest [data k]
  (let [arr (int-array data)]
    (qs-select arr 0 (dec (alength arr)) k)))

(defn percentile [scores percentile-rank]
  (let [rank   (/ percentile-rank 100.0)
        topidx (- (count scores) 1)
        index  (math/round (* rank topidx))]
    (select-kth-smallest scores index)))

(defn ex-3-4 []
  (doseq [pr (range 10 100 10)]
    (println (format "percentile(%d) = %d" pr (percentile data pr)))))

;; ===========================================================================

(defn -main []
  (ex-3-4))

