call runcrud
if "%ERRORLEVEL%" == "0" goto rungettasks
echo.
echo There were errors - breaking work
goto fail

:rungettasks
start "" http://localhost:8080/v1/tasks

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.