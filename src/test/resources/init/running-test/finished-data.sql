INSERT INTO running_tests (start_time, account_id, test_id)
VALUES ('2022-11-27 04:08:40.993459', 1, 2),
       ('2022-11-27 04:13:53.964341', 1, 2);
INSERT INTO tests_results (completion_date, score, account_id, running_test_id)
VALUES ('2022-11-27 04:13:53.943498', 0, 1, 1),
       ('2022-11-27 04:20:21.876548', 100, 1, 2);
UPDATE running_tests Set test_result_id = 1 where id = 1;
UPDATE running_tests Set test_result_id = 2 where id = 2;
