// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Widget {

    public void swapItem(int id0, int id1) {
        int id = itemIds[id0];
        itemIds[id0] = itemIds[id1];
        itemIds[id1] = id;
        id = itemAmounts[id0];
        itemAmounts[id0] = itemAmounts[id1];
        itemAmounts[id1] = id;
    }

    public static void loadWidgets(ArchivePackage widgetarchive, BitmapFont fonts[], ArchivePackage spritearchive)
    {
        spriteCache = new Cache(50000);
        ByteBuffer dataBuffer = new ByteBuffer(widgetarchive.getArchive("data", null));
        int parentId = -1;
        int amountWidgets = dataBuffer.getUword();
        widgets = new Widget[amountWidgets];	
        while(dataBuffer.offset < dataBuffer.payload.length) {
            int widgetId = dataBuffer.getUword();
            if(widgetId == 65535) {
                parentId = dataBuffer.getUword();
                widgetId = dataBuffer.getUword();
            }
            Widget widget = widgets[widgetId] = new Widget();
            widget.widgetId = widgetId;
            widget.parentId = parentId;
            widget.type = dataBuffer.getUbyte();
            widget.fieldType = dataBuffer.getUbyte();
            widget.actionCode = dataBuffer.getUword();
            widget.width = dataBuffer.getUword();
            widget.height = dataBuffer.getUword();
            widget.alpha = (byte)dataBuffer.getUbyte();
            widget.childId = dataBuffer.getUbyte();
            if(widget.childId != 0)
                widget.childId = (widget.childId - 1 << 8) + dataBuffer.getUbyte();
            else
                widget.childId = -1;
            int amountScriptInstructions = dataBuffer.getUbyte();
            if(amountScriptInstructions > 0) {
                widget.scriptInstructions = new int[amountScriptInstructions];
                widget.scriptConditions = new int[amountScriptInstructions];
                for(int j1 = 0; j1 < amountScriptInstructions; j1++) {
                    widget.scriptInstructions[j1] = dataBuffer.getUbyte();
                    widget.scriptConditions[j1] = dataBuffer.getUword();
                }
            }
            int amountScripts = dataBuffer.getUbyte();
            if(amountScripts > 0)
            {
                widget.scriptOpcodes = new int[amountScripts][];
                for(int l1 = 0; l1 < amountScripts; l1++) {
                    int scriptSize = dataBuffer.getUword();
                    widget.scriptOpcodes[l1] = new int[scriptSize];
                    for(int l4 = 0; l4 < scriptSize; l4++)
                        widget.scriptOpcodes[l1][l4] = dataBuffer.getUword();
                }
            }
            if(widget.type == 0)
            {
                widget.curHeight = dataBuffer.getUword();
                widget.isActive = dataBuffer.getUbyte() == 1;
                int amountchildren = dataBuffer.getUword();
                widget.childrenIds = new int[amountchildren];
                widget.childrenOffX = new int[amountchildren];
                widget.childrenOffY = new int[amountchildren];
                for(int g = 0; g < amountchildren; g++)
                {
                    widget.childrenIds[g] = dataBuffer.getUword();
                    widget.childrenOffX[g] = dataBuffer.getWord();
                    widget.childrenOffY[g] = dataBuffer.getWord();
                }

            }
            if(widget.type == 1)
            {
                widget.unusedInt = dataBuffer.getUword();
                widget.unusedBool = dataBuffer.getUbyte() == 1;
            }
            if(widget.type == 2)
            {
                widget.itemIds = new int[widget.width * widget.height];
                widget.itemAmounts = new int[widget.width * widget.height];
                widget.aBoolean259 = dataBuffer.getUbyte() == 1;
                widget.aBoolean249 = dataBuffer.getUbyte() == 1;
                widget.aBoolean242 = dataBuffer.getUbyte() == 1;
                widget.aBoolean235 = dataBuffer.getUbyte() == 1;
                widget.xOff = dataBuffer.getUbyte();
                widget.yOff = dataBuffer.getUbyte();
                widget.anIntArray215 = new int[20];
                widget.anIntArray247 = new int[20];
                widget.sprites = new DirectColorSprite[20];
                for(int j2 = 0; j2 < 20; j2++)
                {
                    int k3 = dataBuffer.getUbyte();
                    if(k3 == 1)
                    {
                        widget.anIntArray215[j2] = dataBuffer.getWord();
                        widget.anIntArray247[j2] = dataBuffer.getWord();
                        String s1 = dataBuffer.getString();
                        if(spritearchive != null && s1.length() > 0)
                        {
                            int i5 = s1.lastIndexOf(",");
                            widget.sprites[j2] = getSprite(spritearchive, s1.substring(0, i5), Integer.parseInt(s1.substring(i5 + 1)));
                        }
                    }
                }

                widget.itemOptions = new String[5];
                for(int l3 = 0; l3 < 5; l3++)
                {
                    widget.itemOptions[l3] = dataBuffer.getString();
                    if(widget.itemOptions[l3].length() == 0)
                        widget.itemOptions[l3] = null;
                }

            }
            if(widget.type == 3)
                widget.isSolidQuad = dataBuffer.getUbyte() == 1;
            if(widget.type == 4 || widget.type == 1)
            {
                widget.isTextCentered = dataBuffer.getUbyte() == 1;
                int fontId = dataBuffer.getUbyte();
                if(fonts != null)
                    widget.textFont = fonts[fontId];
                widget.drawTextShadow = dataBuffer.getUbyte() == 1;
            }
            if(widget.type == 4)
            {
                widget.inactiveText = dataBuffer.getString();
                widget.activeText = dataBuffer.getString();
            }
            if(widget.type == 1 || widget.type == 3 || widget.type == 4)
                widget.inactiveTextColor = dataBuffer.getDword();
            if(widget.type == 3 || widget.type == 4)
            {
                widget.activeTextColor = dataBuffer.getDword();
                widget.anInt216 = dataBuffer.getDword();
                widget.anInt239 = dataBuffer.getDword();
            }
            if(widget.type == 5)
            {
                String s = dataBuffer.getString();
                if(spritearchive != null && s.length() > 0)
                {
                    int i = s.lastIndexOf(",");
                    widget.inactiveSprite = getSprite(spritearchive, s.substring(0, i), Integer.parseInt(s.substring(i + 1)));
                }
                s = dataBuffer.getString();
                if(spritearchive != null && s.length() > 0)
                {
                    int i = s.lastIndexOf(",");
                    widget.activeSprite = getSprite(spritearchive, s.substring(0, i), Integer.parseInt(s.substring(i + 1)));
                }
            }
            if(widget.type == 6)
            {
                int modelId = dataBuffer.getUbyte();
                if(modelId != 0)
                {
                    widget.unactiveAnimFetchType = 1;
                    widget.unactiveAnimModelId = (modelId - 1 << 8) + dataBuffer.getUbyte();
                }
                modelId = dataBuffer.getUbyte();
                if(modelId != 0)
                {
                    widget.activeAnimFetchType = 1;
                    widget.activeAnimModelId = (modelId - 1 << 8) + dataBuffer.getUbyte();
                }
                modelId = dataBuffer.getUbyte();
                if(modelId != 0)
                    widget.inactiveAnimId = (modelId - 1 << 8) + dataBuffer.getUbyte();
                else
                    widget.inactiveAnimId = -1;
                modelId = dataBuffer.getUbyte();
                if(modelId != 0)
                    widget.activeAnimId = (modelId - 1 << 8) + dataBuffer.getUbyte();
                else
                    widget.activeAnimId = -1;
                widget.rotationOrigin = dataBuffer.getUword();
                widget.rotationAngleX = dataBuffer.getUword();
                widget.rotationAngleY = dataBuffer.getUword();
            }
            if(widget.type == 7)
            {
                widget.itemIds = new int[widget.width * widget.height];
                widget.itemAmounts = new int[widget.width * widget.height];
                widget.isTextCentered = dataBuffer.getUbyte() == 1;
                int fontid = dataBuffer.getUbyte();
                if(fonts != null)
                    widget.textFont = fonts[fontid];
                widget.drawTextShadow = dataBuffer.getUbyte() == 1;
                widget.inactiveTextColor = dataBuffer.getDword();
                widget.xOff = dataBuffer.getWord();
                widget.yOff = dataBuffer.getWord();
                widget.aBoolean249 = dataBuffer.getUbyte() == 1;
                widget.itemOptions = new String[5];
                for(int k4 = 0; k4 < 5; k4++)
                {
                    widget.itemOptions[k4] = dataBuffer.getString();
                    if(widget.itemOptions[k4].length() == 0)
                        widget.itemOptions[k4] = null;
                }
            }
            if(widget.fieldType == 2 || widget.type == 2)
            {
                widget.aString222 = dataBuffer.getString();
                widget.aString218 = dataBuffer.getString();
                widget.anInt237 = dataBuffer.getUword();
            }
            if(widget.type == 8)
                dataBuffer.getString();
            if(widget.fieldType == 1 || widget.fieldType == 4 || widget.fieldType == 5 || widget.fieldType == 6)
            {
                widget.optionField = dataBuffer.getString();
                if(widget.optionField.length() == 0)
                {
                    if(widget.fieldType == 1)
                        widget.optionField = "Ok";
                    if(widget.fieldType == 4)
                        widget.optionField = "Select";
                    if(widget.fieldType == 5)
                        widget.optionField = "Select";
                    if(widget.fieldType == 6)
                        widget.optionField = "Continue";
                }
            }
        }
        spriteCache = null;
    }

    public Model getModel(int fetchType, int modelId)
    {
        Model model = (Model) modelCache.get((fetchType << 16) + modelId);
        if(model != null)
            return model;
        if(fetchType == 1)
            model = Model.getModel(modelId);
        if(fetchType == 2)
            model = NpcDefinition.getNPCDefinition(modelId).method160(true);
        if(fetchType == 3)
            model = Main.localPlayer.method453();
        if(fetchType == 4)
            model = ItemDefinition.getItemDefinition(modelId).method202(50, true);
        if(fetchType == 5)
            model = null;
        if(model != null)
            modelCache.put(model, (fetchType << 16) + modelId);
        return model;
    }

    public static DirectColorSprite getSprite(ArchivePackage pack, String spriteName, int childId)
    {
        long hash = (TextTools.hashString((byte)1, spriteName) << 8) + (long) childId;
        DirectColorSprite sprite = (DirectColorSprite) spriteCache.get(hash);
        if(sprite != null)
            return sprite;
        try
        {
            sprite = new DirectColorSprite(pack, spriteName, childId);
            spriteCache.put(sprite, hash);
        }
        catch(Exception ex)
        {
            return null;
        }
        return sprite;
    }

    public static void cacheModel(int c, int a, Model model)
    {
        modelCache.clear();
        if(model != null && a != 4)
            modelCache.put(model, (a << 16) + c);
    }

    public Model getAnimatedModel(int frameId1, int frameId0, boolean isActive)
    {
        Model model;
        if(isActive)
            model = getModel(activeAnimFetchType, activeAnimModelId);
        else
            model = getModel(unactiveAnimFetchType, unactiveAnimModelId);
        if(model == null)
            return null;
        if(frameId0 == -1 && frameId1 == -1 && model.trianglecolors == null)
            return model;
        Model model1 = new Model(model,true, AnimFrame.method532(frameId0, false) & AnimFrame.method532(frameId1, false), false);
        if(frameId0 != -1 || frameId1 != -1)
            model1.setVertexTriangleGroups();
        if(frameId0 != -1)
            model1.applyAnimationFrame(frameId0);
        if(frameId1 != -1)
            model1.applyAnimationFrame(frameId1);
        model1.setLightingVectors(64, 768, -50, -10, -50, true);
            return model1;
    }

    public Widget()
    {
        anInt213 = 9;
        anInt229 = 891;
    }

    public DirectColorSprite inactiveSprite;
    public int anInt208;
    public DirectColorSprite sprites[];
    public static Widget widgets[];
    public int unusedInt;
    public int scriptConditions[];
    public int anInt213;
    public int actionCode;
    public int anIntArray215[];
    public int anInt216;
    public int fieldType;
    public String aString218;
    public int activeTextColor;
    public int width;
    public String optionField;
    public String aString222;
    public boolean isTextCentered;
    public int anInt224;
    public String itemOptions[];
    public int scriptOpcodes[][];
    public boolean isSolidQuad;
    public String activeText;
    public int anInt229;
    public int childId;
    public int xOff;
    public int inactiveTextColor;
    public int unactiveAnimFetchType;
    public int unactiveAnimModelId;
    public boolean aBoolean235;
    public int parentId;
    public int anInt237;
    public static Cache spriteCache;
    public int anInt239;
    public int childrenIds[];
    public int childrenOffX[];
    public boolean aBoolean242;
    public BitmapFont textFont;
    public int yOff;
    public int scriptInstructions[];
    public int anInt246;
    public int anIntArray247[];
    public String inactiveText;
    public boolean aBoolean249;
    public int widgetId;
    public boolean unusedBool;
    public int itemAmounts[];
    public int itemIds[];
    public byte alpha;
    public int activeAnimFetchType;
    public int activeAnimModelId;
    public int inactiveAnimId;
    public int activeAnimId;
    public boolean aBoolean259;
    public DirectColorSprite activeSprite;
    public int curHeight;
    public int type;
    public int offsetX;
    public static Cache modelCache = new Cache(30);
    public int offsetY;
    public boolean isActive;
    public int height;
    public boolean drawTextShadow;
    public int rotationOrigin;
    public int rotationAngleX;
    public int rotationAngleY;
    public int childrenOffY[];

}
