package command

import settings.Settings

class List extends CommandWithCache(Settings.listCache, "git issue list") {
    def help = "list"
}

class Project extends CommandWithCache(Settings.projectCache, "git issue project") {
    def help = "project"
}

class Mine extends CommandWithCache(Settings.mineCache, "git issue mine") {
    def help = "mine"
}