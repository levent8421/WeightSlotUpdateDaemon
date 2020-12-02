TARGET_PATH = 'D:/upgrade-target'

def cleanup() {
    try {
        tools.delete(TARGET_PATH, true)
    } catch (IOException e) {
        log.warn("Cannot delete ${TARGET_PATH}", e)
    }
    tools.mkdirs(TARGET_PATH)
}

def moveAppFile() {
    tools.mv("${workspace}/digital_weight_sensor.jar", "${TARGET_PATH}/app/digital_weight_sensor.jar")
}

def moveScript() {
    scripts = [
            'mysql-shell.sh',
            'deploy.sh',
            'remote-debug.sh',
            'shutdown.sh',
            'startup.sh',
            'sync-from-haiya.sh',
            'tail-log.sh',
    ]
    scripts.each {
        target = "${TARGET_PATH}/app/${it}"
        source = "${workspace}/${it}"
        tools.mv(source, target)
        tools.chmod(target, true, true, true)
    }
}

def moveDbScript() {

}

def moveFile() {
    tools.mkdirs("${TARGET_PATH}/app")
    tools.mkdirs("${TARGET_PATH}/db")
    tools.mkdirs("${TARGET_PATH}/so")
    moveAppFile()
    moveScript()
    moveDbScript()
}