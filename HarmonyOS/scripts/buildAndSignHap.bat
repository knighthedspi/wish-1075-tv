@echo off
cd ..
call gradlew.bat clean --stacktrace
call gradlew.bat aD
cd build\outputs\hap\debug
set current_dir="%cd%"
for /r %current_dir% %%f in (*rich-signed.hap) do (
    call demoSignHap.bat %%f
    timeout /t 5
)
exit
