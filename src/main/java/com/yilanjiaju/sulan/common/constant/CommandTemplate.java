package com.yilanjiaju.sulan.common.constant;

public enum CommandTemplate {
    grep ("grep", "fgrep ${extendCommand} -r ${keyword} ${path} -m ${lines}"),
    tac ("tac", "tac ${path} | grep -m ${lines} ${extendCommand} ${keyword}"),
    head ("head", "head -n ${lines} ${path}"),
    tail ("tail", "tail -n ${lines} ${path}");

    private String name;
    private String template;

    CommandTemplate(String n, String t){
        this.name = n;
        this.template = t;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }

    public static String getTemplateByName(String name) {
        for (CommandTemplate template : CommandTemplate.values()) {
            if(template.getName().equals(name)) {
                return template.getTemplate();
            }
        }
        return "";
    }
}
