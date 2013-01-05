(ns thinkstats.charts
  (:import org.jfree.chart.axis.LogAxis))

(defn log-scale-y [chart]
  (let [plot (.getPlot chart)
        axis (LogAxis.)]
    (.setRangeAxis plot 0 axis))
  chart)
