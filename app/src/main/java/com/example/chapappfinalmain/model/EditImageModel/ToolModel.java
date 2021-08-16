package com.example.chapappfinalmain.model.EditImageModel;

import com.example.chapappfinalmain.Enum.ToolType;

public class ToolModel {
    private String toolName;
    private int toolIcon;
    private ToolType toolType;

    public ToolModel(String toolName, int toolIcon, ToolType toolType) {
        this.toolName = toolName;
        this.toolIcon = toolIcon;
        this.toolType = toolType;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public int getToolIcon() {
        return toolIcon;
    }

    public void setToolIcon(int toolIcon) {
        this.toolIcon = toolIcon;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }
}
