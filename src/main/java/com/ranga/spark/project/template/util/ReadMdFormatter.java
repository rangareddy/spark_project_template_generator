package com.ranga.spark.project.template.util;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.*;

public class ReadMdFormatter implements Serializable {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("MdFormatter <file_path>");
            System.exit(0);
        }
        String mdFileName = args[0];
        processMDFile(mdFileName);
    }

    public static void processMDFile(String mdFileName) {
        File mdFile = new File(mdFileName);
        String dirPath = mdFile.getParent();
        String fileName = mdFile.getName();
        String newFileName = fileName.replace(".md", "_New.md");
        getHeaderContent(mdFile, dirPath + File.separator + newFileName);
    }

    private static void getHeaderContent(File mdFile, String newFileName) {
        List<String> headerList = new ArrayList<>();
        boolean isRunInteractively = false, isRunAsProject = false;
        try (FileWriter outputWriter = new FileWriter(newFileName)) {
            List<String> lines = Files.readAllLines(mdFile.toPath());
            for(String st: lines) {
                if (st.trim().startsWith("#")) {
                    if(st.trim().indexOf("#") != st.trim().lastIndexOf("#")) {
                        headerList.add(st);
                    }

                    if(!isRunInteractively && st.trim().contains("Run the code Interactively")) {
                        isRunInteractively = true;
                    }

                    if(!isRunAsProject &&  st.trim().contains("Run the code as Project")) {
                        isRunAsProject = true;
                    }
                }
            }

            int headerCount = 0, subHeaderCount = 0;
            boolean isPrerequisites, isRunAsProjectStarted, isRunInteractiveStarted;
            isPrerequisites = isRunAsProjectStarted = isRunInteractiveStarted = false;
            StringBuilder sb = new StringBuilder();
            for(String header: headerList) {
                String st[] = header.split("##");
                boolean isTwoDelim = header.indexOf("##") == header.lastIndexOf("##");
                boolean isThreeDelim = header.indexOf("###") == header.lastIndexOf("###");
                if(header.contains("Prerequisites") && !isPrerequisites) {
                    isPrerequisites = true;
                }
                if(isPrerequisites) {
                    if(isTwoDelim) {
                        sb.append("## ").append(++headerCount).append(". ").append(st[1]).append("\n");
                    }
                    if(!isRunInteractively && !isRunAsProject) {
                        sb.append("## ").append(++headerCount).append(". ").append("Run the code as a project").append("\n");
                        isPrerequisites = false;
                        isRunAsProjectStarted = true;
                    }
                } else if(isRunAsProjectStarted) {
                    String headerIndex = headerCount +"."+ (++subHeaderCount);
                    if(isTwoDelim) {
                        sb.append("## ").append(headerIndex).append(". ").append(st[1].trim()).append("\n");
                    } else if(isThreeDelim) {
                        String tempHeader = header.replace("###", "").trim();
                        sb.append("### ").append(headerIndex).append(". ").append(tempHeader).append("\n");
                    }
                } else {
                    System.out.println(header);
                }
            }

            for(String st: lines) {
                if (st.contains("#") && st.trim().indexOf("#") == st.trim().lastIndexOf("#")) {
                    outputWriter.write(st + "\n");
                } else if(st.trim().contains("Prerequisites")) {
                    outputWriter.write("# Table of Contents\n-------------------------\n");
                    outputWriter.write(sb + "\n");

                    outputWriter.write(st + "\n");
                } else {
                    outputWriter.write(st + "\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}