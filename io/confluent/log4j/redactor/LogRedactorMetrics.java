/*
 * Copyright (c) 2021, Confluent, Inc.
 */

package io.confluent.log4j.redactor;

import java.util.Map;

/**
 * A pluggable metrics interface for log redactor to write metrics. It provides three methods:
 * <ul>
 *   <li>count: called when an event occurs.</li>
 *   <li>timer: called after measuring the time elapsed for an event.</li>
 *   <li>gauge: called after measuring a "current" value.</li>
 * </ul>
 * <p>
 * Each metric has a name and zero or more tags. Tags are key-value pairs of Strings. Metric names
 * and tag keys contain only letters, numbers, and underscores. Tag values may potentially contain
 * any String. Implementors of the interface must escape and truncate tag values to fit their own
 * validation requirements.
 */
public interface LogRedactorMetrics {

  String COUNT_POLICY_UPDATE = "policy_update";
  String COUNT_ERROR = "error";
  String COUNT_REDACTIONS = "redactions";
  String COUNT_MATCHES = "matches";
  String COUNT_SCANNED_LOG_STATEMENTS = "scanned_log_statements";
  String COUNT_REDACTED_LOG_STATEMENTS = "redacted_log_statements";
  String COUNT_MATCHED_LOG_STATEMENTS = "matched_log_statements";
  String TIMER_READ_POLICY_SECONDS = "read_policy_seconds";
  String GAUGE_POLICY_RULE_COUNT = "policy_rule_count";

  /**
   * Called when an event occurs.
   *
   * @param metricName The name of the metric. May contain alpha-numerics and underscore.
   * @param tags        Key-value pairs.
   */
  void count(String metricName, Map<String, String> tags);

  /**
   * Called after measuring the time elapsed for an event.
   *
   * @param value      Elapsed time, in seconds.
   * @param metricName The name of the metric. May contain alpha-numerics and underscore.
   * @param tags        Key-value pairs.
   */
  void timer(double value, String metricName, Map<String, String> tags);

  /**
   * Called after measuring a "current" value.
   *
   * @param value      The "current" value, as measured.
   * @param metricName The name of the metric. May contain alpha-numerics and underscore.
   * @param tags        Key-value pairs.
   */
  void gauge(double value, String metricName, Map<String, String> tags);

  /**
   * A no-op implementation, that ignores all metrics rather than collecting them.
   */
  LogRedactorMetrics NOOP = new LogRedactorMetrics() {
    @Override public void count(String metricName, Map<String, String> tags) {}
    @Override public void timer(double value, String metricName, Map<String, String> tags) {}
    @Override public void gauge(double value, String metricName, Map<String, String> tags) {}
  };
}

