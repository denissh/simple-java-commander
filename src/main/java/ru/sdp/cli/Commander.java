package ru.sdp.cli;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Commander {
    private Map<String, Class<? extends Runnable>> mapp = new LinkedHashMap<>();
    private Map<String, Runnable> readyCommands = new HashMap<>();
    private String programName = "program";
    private String currCmd = "";

    //kt
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    //kt
    public Commander add(Class<? extends Runnable> clazz) throws IllegalAccessException, InstantiationException {
        Cmd annotation = clazz.getAnnotation(Cmd.class);
        if (annotation == null) {
            throw new IllegalStateException("Need @Cmd for class " + clazz.getSimpleName());
        }
        String name = annotation.name();
        mapp.put(name, clazz);
        return this;
    }

    public void run(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("No arguments");
        }
        Class<? extends Runnable> clazz = mapp.get(args[0]);
        if (clazz == null) {
            throw new Exception("Command " + args[0] + " not found");
        }
        currCmd = args[0];
        List<String> list = Arrays.stream(args).skip(1).collect(Collectors.toList());
        Runnable command = parse(list.toArray(new String[list.size()]), clazz);
        command.run();
    }

    //kt
    private <T extends Runnable> T parse(String[] args, Class<T> clazz) throws Exception {
        T command = clazz.newInstance();

        Map<String, String> params = parseSingleParams(Arrays.stream(args).collect(Collectors.toList()));
        Map<Field, List<String>> fields = collect(command);

        Set<Field> visitedField = new HashSet<>();

        for (Map.Entry<Field, List<String>> fieldEntry : fields.entrySet()) {
            String paramValue = null;
            for (String key : fieldEntry.getValue()) {
                if (paramValue == null) {
                    paramValue = params.get(key);
                }
                params.remove(key);
            }

            Field field = fieldEntry.getKey();
            if (String.class.equals(field.getType())) {
                if (paramValue != null) {
                    setFieldValue(command, paramValue, field);
                    visitedField.add(field);
                }
            } else if (Date.class.equals(field.getType())) {
                if (paramValue != null) {
                    String formatStr = null;
                    if (field.isAnnotationPresent(Param.class)) {
                        Param param = field.getAnnotation(Param.class);
                        formatStr = param.format();
                    }
                    if (formatStr == null || formatStr.isEmpty()) {
                        throw new IllegalStateException("For field " + field.getName() + " with type Date need format, for example dd.MM.yyyy");
                    }

                    setFieldValue(command, new SimpleDateFormat(formatStr).parse(paramValue), field);

                    visitedField.add(field);
                }
            } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                if (paramValue != null) {
                    setFieldValue(command, Double.valueOf(paramValue), field);
                    visitedField.add(field);
                }
            } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                if (paramValue != null) {
                    setFieldValue(command, Float.valueOf(paramValue), field);
                    visitedField.add(field);
                }
            } else if (long.class.equals(field.getType()) || Long.class.equals(field.getType())) {
                if (paramValue != null) {
                    setFieldValue(command, Long.valueOf(paramValue), field);
                    visitedField.add(field);
                }
            } else if (int.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
                if (paramValue != null) {
                    setFieldValue(command, Integer.valueOf(paramValue), field);
                    visitedField.add(field);
                }
            } else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
                String value;
                if (paramValue == null) {
                    value = "false";
                } else if ("".equals(paramValue)) {
                    value = "true";
                } else {
                    value = paramValue;
                }
                setFieldValue(command, Boolean.valueOf(value), field);
                visitedField.add(field);
            }
        }
        if (!params.isEmpty()) {
            String text = params.entrySet().stream().map(entry -> entry.getKey() + " " + entry.getValue()).collect(Collectors.joining(", "));
            throw new Exception("Unnecessary params for command " + currCmd + ": " + text);
        }
        //todo process fields without params
        for (Field field : visitedField) {
            fields.remove(field);
        }
        return command;
    }

    //kt
    private void setFieldValue(Object command, Object value, Field field) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(command, value);
        field.setAccessible(accessible);
    }

    private <T extends Runnable> Map<Field, List<String>> collect(T command) {
        Map<Field, List<String>> fields = new HashMap<>();
        for (Field field : command.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Param.class)) {
                Param param = field.getAnnotation(Param.class);
                fields.put(field, Arrays.asList(param.names()));
            }
        }
        return fields;
    }

    //kt
    public Class<? extends Runnable> get(String cmdName) {
        return mapp.get(cmdName);
    }

    //kt
    public Map<String, String> parseSingleParams(List<String> args) {
        Map<String, String> params = new HashMap<>();

        String key = "";
        for (String arg : args) {
            if (arg.startsWith("-") || arg.startsWith("--")) {
                key = arg;
                params.put(key, "");
            } else if (!key.isEmpty()) {
                params.put(key, arg);
            }
        }
        return params;
    }

    //kt
    public Map<String, List<String>> parseMultipleParams(List<String> args) {
        Map<String, List<String>> params = new HashMap<>();

        String key = "";
        for (String arg : args) {
            if (arg.startsWith("-") || arg.startsWith("--") || arg.startsWith("--")) {
                key = arg;
                params.putIfAbsent(key, new ArrayList<>());
            } else if (!key.isEmpty()) {
                params.get(key).add(arg);
            }
        }
        return params;
    }

    //kt
    public List<String> getUsageLines() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Usage: " + programName + " command [options]");
        list.add("Commands:");
        for (Map.Entry<String, Class<? extends Runnable>> entry : mapp.entrySet()) {
            Class<? extends Runnable> clazz = entry.getValue();
            if (clazz.isAnnotationPresent(Cmd.class)) {
                list.add("    " + entry.getKey() + " " + clazz.getAnnotation(Cmd.class).description());
            }
        }
        return list;
    }
}
