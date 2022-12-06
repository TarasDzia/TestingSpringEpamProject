INSERT INTO running_tests (start_time, account_id, test_id, test_result_id)
VALUES ('2022-11-27 04:08:40.993459', 1, 2, null),
       (NOW(), 1, 1, null);

INSERT INTO tests_results (completion_date, score, account_id, running_test_id)
VALUES ('2022-11-27 04:13:53.943498', 100, 1, 1);
UPDATE running_tests Set test_result_id = 1 where id = 1;