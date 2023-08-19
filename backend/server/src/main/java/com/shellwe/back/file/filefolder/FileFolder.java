package com.shellwe.back.file.filefolder;

public enum FileFolder {

    MEMBER_FOLDER("member/"),
    SHELL_FOLDER("shell/");

    private final String folderName;

    FileFolder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return this.folderName;
    }
}
