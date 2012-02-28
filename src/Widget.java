// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class Widget {

    public void initializeWidgets(int i, byte byte0, int j) {
        int k = itemarray[i];
        itemarray[i] = itemarray[j];
        itemarray[j] = k;
        k = itemamounts[i];
        itemamounts[i] = itemamounts[j];
        itemamounts[j] = k;
    }

    public static void unpackWidgets(ArchivePackage widgetarchive, BitmapFont fonts[], ArchivePackage spritearchive)
    {
        aClass12_238 = new Cache(50000);
        ByteBuffer buffer0 = new ByteBuffer(widgetarchive.getArchive("data", null));
        int i = -1;
        int j = buffer0.getUword();
        widgets = new Widget[j];	
        while(buffer0.offset < buffer0.payload.length) 
        {
            int k = buffer0.getUword();
            if(k == 65535)
            {
                i = buffer0.getUword();
                k = buffer0.getUword();
            }
            Widget widget = widgets[k] = new Widget();
            widget.widgetid = k;
            widget.parentid = i;
            widget.widgettype = buffer0.getUbyte();
            widget.textfieldtype = buffer0.getUbyte();
            widget.actioncode = buffer0.getUword();
            widget.width = buffer0.getUword();
            widget.height = buffer0.getUword();
            widget.oalpha = (byte)buffer0.getUbyte();
            widget.anInt230 = buffer0.getUbyte();
            if(widget.anInt230 != 0)
                widget.anInt230 = (widget.anInt230 - 1 << 8) + buffer0.getUbyte();
            else
                widget.anInt230 = -1;
            int i1 = buffer0.getUbyte();
            if(i1 > 0)
            {
                widget.updateconditions = new int[i1];
                widget.updatestates = new int[i1];
                for(int j1 = 0; j1 < i1; j1++)
                {
                    widget.updateconditions[j1] = buffer0.getUbyte();
                    widget.updatestates[j1] = buffer0.getUword();
                }

            }
            int k1 = buffer0.getUbyte();
            if(k1 > 0)
            {
                widget.anIntArrayArray226 = new int[k1][];
                for(int l1 = 0; l1 < k1; l1++)
                {
                    int i3 = buffer0.getUword();
                    widget.anIntArrayArray226[l1] = new int[i3];
                    for(int l4 = 0; l4 < i3; l4++)
                        widget.anIntArrayArray226[l1][l4] = buffer0.getUword();

                }

            }
            if(widget.widgettype == 0)
            {
                widget.anInt261 = buffer0.getUword();
                widget.aBoolean266 = buffer0.getUbyte() == 1;
                int amountchildren = buffer0.getUword();
                widget.childrenwidgets = new int[amountchildren];
                widget.positionx = new int[amountchildren];
                widget.positiony = new int[amountchildren];
                for(int g = 0; g < amountchildren; g++)
                {
                    widget.childrenwidgets[g] = buffer0.getUword();
                    widget.positionx[g] = buffer0.getWord();
                    widget.positiony[g] = buffer0.getWord();
                }

            }
            /* Dummy widget */
            if(widget.widgettype == 1)
            {
                widget.anInt211 = buffer0.getUword();
                widget.aBoolean251 = buffer0.getUbyte() == 1;
            }
            if(widget.widgettype == 2)
            {
                widget.itemarray = new int[widget.width * widget.height];
                widget.itemamounts = new int[widget.width * widget.height];
                widget.aBoolean259 = buffer0.getUbyte() == 1;
                widget.aBoolean249 = buffer0.getUbyte() == 1;
                widget.aBoolean242 = buffer0.getUbyte() == 1;
                widget.aBoolean235 = buffer0.getUbyte() == 1;
                widget.anInt231 = buffer0.getUbyte();
                widget.anInt244 = buffer0.getUbyte();
                widget.anIntArray215 = new int[20];
                widget.anIntArray247 = new int[20];
                widget.sprites = new DirectColorSprite[20];
                for(int j2 = 0; j2 < 20; j2++)
                {
                    int k3 = buffer0.getUbyte();
                    if(k3 == 1)
                    {
                        widget.anIntArray215[j2] = buffer0.getWord();
                        widget.anIntArray247[j2] = buffer0.getWord();
                        String s1 = buffer0.getString();
                        if(spritearchive != null && s1.length() > 0)
                        {
                            int i5 = s1.lastIndexOf(",");
                            widget.sprites[j2] = getInterfaceSprite(Integer.parseInt(s1.substring(i5 + 1)), spritearchive, s1.substring(0, i5));
                        }
                    }
                }

                widget.itemoptions = new String[5];
                for(int l3 = 0; l3 < 5; l3++)
                {
                    widget.itemoptions[l3] = buffer0.getString();
                    if(widget.itemoptions[l3].length() == 0)
                        widget.itemoptions[l3] = null;
                }

            }
            if(widget.widgettype == 3)
                widget.aBoolean227 = buffer0.getUbyte() == 1;
            if(widget.widgettype == 4 || widget.widgettype == 1)
            {
                widget.centerx = buffer0.getUbyte() == 1;
                int k2 = buffer0.getUbyte();
                if(fonts != null)
                    widget.itemfont = fonts[k2];
                widget.shadowtext = buffer0.getUbyte() == 1;
            }
            if(widget.widgettype == 4)
            {
                widget.aString248 = buffer0.getString();
                widget.hiddentext = buffer0.getString();
            }
            if(widget.widgettype == 1 || widget.widgettype == 3 || widget.widgettype == 4)
                widget.anInt232 = buffer0.getDword();
            if(widget.widgettype == 3 || widget.widgettype == 4)
            {
                widget.anInt219 = buffer0.getDword();
                widget.anInt216 = buffer0.getDword();
                widget.anInt239 = buffer0.getDword();
            }
            if(widget.widgettype == 5)
            {
                String s = buffer0.getString();
                if(spritearchive != null && s.length() > 0)
                {
                    int i4 = s.lastIndexOf(",");
                    widget.aClass30_Sub2_Sub1_Sub1_207 = getInterfaceSprite(Integer.parseInt(s.substring(i4 + 1)), spritearchive, s.substring(0, i4));
                }
                s = buffer0.getString();
                if(spritearchive != null && s.length() > 0)
                {
                    int j4 = s.lastIndexOf(",");
                    widget.aClass30_Sub2_Sub1_Sub1_260 = getInterfaceSprite(Integer.parseInt(s.substring(j4 + 1)), spritearchive, s.substring(0, j4));
                }
            }
            if(widget.widgettype == 6)
            {
                int l = buffer0.getUbyte();
                if(l != 0)
                {
                    widget.anInt233 = 1;
                    widget.anInt234 = (l - 1 << 8) + buffer0.getUbyte();
                }
                l = buffer0.getUbyte();
                if(l != 0)
                {
                    widget.anInt255 = 1;
                    widget.anInt256 = (l - 1 << 8) + buffer0.getUbyte();
                }
                l = buffer0.getUbyte();
                if(l != 0)
                    widget.anInt257 = (l - 1 << 8) + buffer0.getUbyte();
                else
                    widget.anInt257 = -1;
                l = buffer0.getUbyte();
                if(l != 0)
                    widget.anInt258 = (l - 1 << 8) + buffer0.getUbyte();
                else
                    widget.anInt258 = -1;
                widget.anInt269 = buffer0.getUword();
                widget.anInt270 = buffer0.getUword();
                widget.anInt271 = buffer0.getUword();
            }
            if(widget.widgettype == 7)
            {
                widget.itemarray = new int[widget.width * widget.height];
                widget.itemamounts = new int[widget.width * widget.height];
                widget.centerx = buffer0.getUbyte() == 1;
                int fontid = buffer0.getUbyte();
                if(fonts != null)
                    widget.itemfont = fonts[fontid];
                widget.shadowtext = buffer0.getUbyte() == 1;
                widget.anInt232 = buffer0.getDword();
                widget.anInt231 = buffer0.getWord();
                widget.anInt244 = buffer0.getWord();
                widget.aBoolean249 = buffer0.getUbyte() == 1;
                widget.itemoptions = new String[5];
                for(int k4 = 0; k4 < 5; k4++)
                {
                    widget.itemoptions[k4] = buffer0.getString();
                    if(widget.itemoptions[k4].length() == 0)
                        widget.itemoptions[k4] = null;
                }
            }
            if(widget.textfieldtype == 2 || widget.widgettype == 2)
            {
                widget.aString222 = buffer0.getString();
                widget.aString218 = buffer0.getString();
                widget.anInt237 = buffer0.getUword();
            }
            if(widget.textfieldtype == 1 || widget.textfieldtype == 4 || widget.textfieldtype == 5 || widget.textfieldtype == 6)
            {
                widget.aString221 = buffer0.getString();
                if(widget.aString221.length() == 0)
                {
                    if(widget.textfieldtype == 1)
                        widget.aString221 = "Ok";
                    if(widget.textfieldtype == 4)
                        widget.aString221 = "Select";
                    if(widget.textfieldtype == 5)
                        widget.aString221 = "Select";
                    if(widget.textfieldtype == 6)
                        widget.aString221 = "Continue";
                }
            }
        }
        aClass12_238 = null;
    }

    public Model getEntityOnChild(int i, int j)
    {
        Model class30_sub2_sub4_sub6 = (Model)aClass12_264.get((i << 16) + j);
        if(class30_sub2_sub4_sub6 != null)
            return class30_sub2_sub4_sub6;
        if(i == 1)
            class30_sub2_sub4_sub6 = Model.getModel(anInt213, j);
        if(i == 2)
            class30_sub2_sub4_sub6 = NPCDefinition.getNPCDefinition(j).method160(true);
        if(i == 3)
            class30_sub2_sub4_sub6 = Main.localplayer.method453((byte)-41);
        if(i == 4)
            class30_sub2_sub4_sub6 = ItemDefinition.getItemDefinition(j).method202(50, true);
        if(i == 5)
            class30_sub2_sub4_sub6 = null;
        if(class30_sub2_sub4_sub6 != null)
            aClass12_264.put(class30_sub2_sub4_sub6, (i << 16) + j);
        return class30_sub2_sub4_sub6;
    }

    public static DirectColorSprite getInterfaceSprite(int id, ArchivePackage archive, String name)
    {
        long hash = (TextTools.hashString((byte)1, name) << 8) + (long) id;
        DirectColorSprite sprite = (DirectColorSprite) aClass12_238.get(hash);
        if(sprite != null)
            return sprite;
        try
        {
            sprite = new DirectColorSprite(archive, name, id);
            aClass12_238.put(sprite, hash);
        }
        catch(Exception _ex)
        {
            return null;
        }
        return sprite;
    }

    public static void method208(int c, boolean flag, int a, Model model)
    {
        if(flag)
            return;
        aClass12_264.clear();
        if(model != null && a != 4)
            aClass12_264.put(model, (a << 16) + c);
    }

    public Model method209(int i, int j, int k, boolean flag)
    {
        Model model0;
        if(flag)
            model0 = getEntityOnChild(anInt255, anInt256);
        else
            model0 = getEntityOnChild(anInt233, anInt234);
        if(model0 == null)
            return null;
        if(k == -1 && j == -1 && model0.trianglecolors == null)
            return model0;
        Model model1 = new Model(model0,true, AnimFrame.method532(k, false) & AnimFrame.method532(j, false), false);
        if(k != -1 || j != -1)
            model1.setVertexTriangleGroups();
        if(k != -1)
            model1.applyAnimationFrame(k);
        if(j != -1)
            model1.applyAnimationFrame(j);
        model1.setLightingVectors(64, 768, -50, -10, -50, true);
        if(i != 0)
            throw new NullPointerException();
        else
            return model1;
    }

    public Widget()
    {
        anInt213 = 9;
        anInt229 = 891;
    }

    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_207;
    public int anInt208;
    public DirectColorSprite sprites[];
    public static Widget widgets[];
    public int anInt211;
    public int updatestates[];
    public int anInt213;
    public int actioncode;
    public int anIntArray215[];
    public int anInt216;
    public int textfieldtype;
    public String aString218;
    public int anInt219;
    public int width;
    public String aString221;
    public String aString222;
    public boolean centerx;
    public int anInt224;
    public String itemoptions[];
    public int anIntArrayArray226[][];
    public boolean aBoolean227;
    public String hiddentext;
    public int anInt229;
    public int anInt230;
    public int anInt231;
    public int anInt232;
    public int anInt233;
    public int anInt234;
    public boolean aBoolean235;
    public int parentid;
    public int anInt237;
    public static Cache aClass12_238;
    public int anInt239;
    public int childrenwidgets[];
    public int positionx[];
    public boolean aBoolean242;
    public BitmapFont itemfont;
    public int anInt244;
    public int updateconditions[];
    public int anInt246;
    public int anIntArray247[];
    public String aString248;
    public boolean aBoolean249;
    public int widgetid;
    public boolean aBoolean251;
    public int itemamounts[];
    public int itemarray[];
    public byte oalpha;
    public int anInt255;
    public int anInt256;
    public int anInt257;
    public int anInt258;
    public boolean aBoolean259;
    public DirectColorSprite aClass30_Sub2_Sub1_Sub1_260;
    public int anInt261;
    public int widgettype;
    public int anInt263;
    public static Cache aClass12_264 = new Cache(30);
    public int anInt265;
    public boolean aBoolean266;
    public int height;
    public boolean shadowtext;
    public int anInt269;
    public int anInt270;
    public int anInt271;
    public int positiony[];

}
