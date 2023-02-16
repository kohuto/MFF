package cz.cuni.mff.java.homework.jfind;

import java.io.File;
        import java.util.Arrays;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.regex.Pattern;

public class JFind {
    public static void find(String[] args) {
        // procces all parameters
        String directory = args[0];
        List<String> conditions = new LinkedList<>(Arrays.asList(args).subList(1, args.length));

        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        // iterate all file in dir and subdirs
        findInDirectory(dir, "", conditions);
    }

    private static void findInDirectory(File dir, String currentPath, List<String> conditions) {
        String output;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // recursively iterate directories
                findInDirectory(file, currentPath + File.separator + file.getName(), conditions);
            } else {
                // check wheter file mathes conditions
                if (matchesConditions(file, conditions)) {
                    output = currentPath + File.separator + file.getName();
                    System.out.println(output.substring(1));
                }
            }
        }
    }

    private static boolean matchesConditions(File file, List<String> conditions) {
        for (int i = 0; 2*i+1<=conditions.size();i++) {

            String type = conditions.get(2*i);
            String param = conditions.get(2*i+1);

            switch (type) {
                case "-name":
                    if (!file.getName().matches(convertToRegex(param))) {
                        return false;
                    }
                    break;
                case "-iname":
                    if (!file.getName().toLowerCase().matches(convertToRegex(param.toLowerCase()))) {
                        return false;
                    }
                    break;
                case "-regex":
                    if (!file.getName().matches(param)) {
                        return false;
                    }
                    break;
                case "-size":
                    if (file.length() != parseSize(param)) {
                        return false;
                    }
                    break;
                case "-ssize":
                    if (file.length() > parseSize(param)) {
                        return false;
                    }
                    break;
                case "-bsize":
                    if (file.length() < parseSize(param)) {
                        return false;
                    }
                    break;
                default:
                    System.out.println("ERROR");
                    System.exit(1);
            }
        }
        return true;
    }
        private static String convertToRegex(String mask) {
            return mask.replace(".", "\\.").replace("*", ".*").replace("?", ".");
        }

        private static long parseSize(String size) {
            char unit = size.charAt(size.length() - 1);
            long multiplier = 1;
            switch (unit) {
                case 'k':
                    multiplier = 1024;
                    break;
                case 'M':
                    multiplier = 1024 * 1024;
                    break;
                case 'G':
                    multiplier = 1024 * 1024 * 1024;
                    break;
                default:
                    if (!Character.isDigit(unit)) {
                        System.out.println("ERROR");
                        System.exit(1);
                    }
            }
            return Long.parseLong(size.substring(0, size.length() - 1)) * multiplier;
        }
    }

