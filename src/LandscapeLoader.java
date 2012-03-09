/* LandscapeLoader - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class LandscapeLoader {
  public static int anInt123 = (int) (Math.random () * 17.0) - 8;
  public int[] tile_blending1;
  public int[] tile_blending2;
  public int[] averageintensityfloors;
  public int[] tile_blending4;
  public int[] blendingcount;
  public int[][][] heightmap;
  public byte[][][] floormap;
  public static int ml_hieght;
  public boolean aBoolean132 = true;
  public static int anInt133 = (int) (Math.random () * 33.0) - 16;
  public byte[][][] shadowmap;
  public int[][][] tilesettings2;
  public byte[][][] floorshadingtypes;
  public static final int[] anIntArray137 = { 1, 0, -1, 0 };
  public static int anInt138 = 323;
  public int[][] anIntArrayArray139;
  public static final int[] anIntArray140 = { 16, 32, 64, 128 };
  public static boolean aBoolean141;
  public byte[][][] floormap2;
  public boolean aBoolean143 = false;
  public static final int[] anIntArray144 = { 0, -1, 0, 1 };
  public static int anInt145 = 99;
  public int ml_sizex;
  public int ml_sizey;
  public byte[][][] floortexturerotation;
  public byte[][][] tileFlags;
  public int anInt150 = -53;
  public static boolean lowmemory = true;
  public static final int[] anIntArray152 = { 1, 2, 4, 8 };
  public static int anInt153 = -388;
  
  public LandscapeLoader (byte[][][] is, int i, int sizey, int sizex, int[][][] is_2_)
  {
    anInt145 = 99;
    ml_sizex = sizex;
    ml_sizey = sizey;
    while (i >= 0)
      anInt153 = -320;
    heightmap = is_2_;
    tileFlags = is;
    floormap2 = new byte[4][ml_sizex][ml_sizey];
    floormap = new byte[4][ml_sizex][ml_sizey];
    floorshadingtypes = new byte[4][ml_sizex][ml_sizey];
    floortexturerotation = new byte[4][ml_sizex][ml_sizey];
    tilesettings2 = new int[4][ml_sizex + 1][ml_sizey + 1];
    shadowmap = new byte[4][ml_sizex + 1][ml_sizey + 1];
    anIntArrayArray139 = new int[ml_sizex + 1][ml_sizey + 1];
    tile_blending1 = new int[ml_sizey];
    tile_blending2 = new int[ml_sizey];
    averageintensityfloors = new int[ml_sizey];
    tile_blending4 = new int[ml_sizey];
    blendingcount = new int[ml_sizey];
  }
  
  public static int noise(int x, int y) {
    int i_4_ = x + y * 57;
    i_4_ = i_4_ << 13 ^ i_4_;
    int i_5_ = i_4_ * (i_4_ * i_4_ * 15731 + 789221) + 1376312589 & 0x7fffffff;
    return i_5_ >> 19 & 0xff;
  }
  
  public void method171 (PlaneFlags[] class11s, Palette class25, int i) {
    for (int i_6_ = 0; i_6_ < 4; i_6_++) {
		for (int i_7_ = 0; i_7_ < 104; i_7_++) {
			for (int i_8_ = 0; i_8_ < 104; i_8_++) {
				if ((tileFlags[i_6_][i_7_][i_8_] & 0x1) == 1) {
					int i_9_ = i_6_;
					if ((tileFlags[1][i_7_][i_8_] & 0x2) == 2)
						i_9_--;
					if (i_9_ >= 0)
						class11s[i_9_].method213 (i_8_, i_7_);
				}
			}
		}
    }
    if (i < 2 || i > 2)
      anInt138 = 329;
    anInt123 += (int) (Math.random () * 5.0) - 2;
    if (anInt123 < -8)
      anInt123 = -8;
    if (anInt123 > 8)
      anInt123 = 8;
    anInt133 += (int) (Math.random () * 5.0) - 2;
    if (anInt133 < -16)
      anInt133 = -16;
    if (anInt133 > 16)
      anInt133 = 16;
    for (int i_10_ = 0; i_10_ < 4; i_10_++) {
		byte[][] is = shadowmap[i_10_];
		int i_11_ = 96;
		int i_12_ = 768;
		int i_13_ = -50;
		int i_14_ = -10;
		int i_15_ = -50;
		int i_16_ = (int) Math.sqrt ((double) (i_13_ * i_13_ + i_14_ * i_14_ + i_15_ * i_15_));
		int i_17_ = i_12_ * i_16_ >> 8;
		for (int i_18_ = 1; i_18_ < ml_sizey - 1; i_18_++) {
			for (int i_19_ = 1; i_19_ < ml_sizex - 1; i_19_++) {
				int i_20_ = (heightmap[i_10_][i_19_ + 1][i_18_] - heightmap[i_10_][i_19_ - 1][i_18_]);
				int i_21_ = (heightmap[i_10_][i_19_][i_18_ + 1] - heightmap[i_10_][i_19_][i_18_ - 1]);
				int i_22_ = (int) Math.sqrt ((double) (i_20_ * i_20_ + 65536 + i_21_ * i_21_));
				int i_23_ = (i_20_ << 8) / i_22_;
				int i_24_ = 65536 / i_22_;
				int i_25_ = (i_21_ << 8) / i_22_;
				int i_26_ = i_11_ + (i_13_ * i_23_ + i_14_ * i_24_ + i_15_ * i_25_) / i_17_;
				int i_27_ = ((is[i_19_ - 1][i_18_] >> 2) + (is[i_19_ + 1][i_18_] >> 3) + (is[i_19_][i_18_ - 1] >> 2) + (is[i_19_][i_18_ + 1] >> 3) + (is[i_19_][i_18_] >> 1));
				anIntArrayArray139[i_19_][i_18_] = i_26_ - i_27_;
			}
		}
		for (int i_28_ = 0; i_28_ < ml_sizey; i_28_++) {
			tile_blending1[i_28_] = 0;
			tile_blending2[i_28_] = 0;
			averageintensityfloors[i_28_] = 0;
			tile_blending4[i_28_] = 0;
			blendingcount[i_28_] = 0;
		}
		for (int i_29_ = -5; i_29_ < ml_sizex + 5; i_29_++) {
			for (int i_30_ = 0; i_30_ < ml_sizey; i_30_++) {
				int i_31_ = i_29_ + 5;
				if (i_31_ >= 0 && i_31_ < ml_sizex) {
					int i_32_ = floormap2[i_10_][i_31_][i_30_] & 0xff;
					if (i_32_ > 0) {
						FloorDefinition class22 = FloorDefinition.definitions[i_32_ - 1];
						tile_blending1[i_30_] += class22.anInt397;
						tile_blending2[i_30_] += class22.iArc;
						averageintensityfloors[i_30_] += class22.mChannel;
						tile_blending4[i_30_] += class22.anInt398;
						blendingcount[i_30_]++;
					}
				}
				int i_33_ = i_29_ - 5;
				if (i_33_ >= 0 && i_33_ < ml_sizex) {
					int i_34_ = floormap2[i_10_][i_33_][i_30_] & 0xff;
					if (i_34_ > 0) {
						FloorDefinition class22 = FloorDefinition.definitions[i_34_ - 1];
						tile_blending1[i_30_] -= class22.anInt397;
						tile_blending2[i_30_] -= class22.iArc;
						averageintensityfloors[i_30_] -= class22.mChannel;
						tile_blending4[i_30_] -= class22.anInt398;
						blendingcount[i_30_]--;
					}
				}
			}
	    if (i_29_ >= 1 && i_29_ < ml_sizex - 1) {
			int i_35_ = 0;
			int i_36_ = 0;
			int i_37_ = 0;
			int i_38_ = 0;
			int i_39_ = 0;
			for (int i_40_ = -5; i_40_ < ml_sizey + 5; i_40_++) {
				int i_41_ = i_40_ + 5;
				if (i_41_ >= 0 && i_41_ < ml_sizey) {
					i_35_ += tile_blending1[i_41_];
					i_36_ += tile_blending2[i_41_];
					i_37_ += averageintensityfloors[i_41_];
					i_38_ += tile_blending4[i_41_];
					i_39_ += blendingcount[i_41_];
				}
				int i_42_ = i_40_ - 5;
				if (i_42_ >= 0 && i_42_ < ml_sizey) {
					i_35_ -= tile_blending1[i_42_];
					i_36_ -= tile_blending2[i_42_];
					i_37_ -= averageintensityfloors[i_42_];
					i_38_ -= tile_blending4[i_42_];
					i_39_ -= blendingcount[i_42_];
				}
				if (i_40_ >= 1 && i_40_ < ml_sizey - 1 && (! lowmemory || (tileFlags[0][i_29_][i_40_] & 0x2) != 0 || ((tileFlags[i_10_][i_29_][i_40_] & 0x10) == 0 && (getHeightFromTilesettings (i_40_, i_10_, i_29_, 0) == ml_hieght)))) {
					if (i_10_ < anInt145)
					  anInt145 = i_10_;
				int i_43_ = (floormap2[i_10_][i_29_][i_40_] & 0xff);
				int i_44_ = (floormap[i_10_][i_29_][i_40_] & 0xff);
				if (i_43_ > 0 || i_44_ > 0) {
					int i_45_ = heightmap[i_10_][i_29_][i_40_];
					int i_46_ = (heightmap[i_10_][i_29_ + 1][i_40_]);
					int i_47_ = (heightmap[i_10_][i_29_ + 1][i_40_ + 1]);
					int i_48_ = (heightmap[i_10_][i_29_]
						 [i_40_ + 1]);
					int i_49_ = anIntArrayArray139[i_29_][i_40_];
					int i_50_ = anIntArrayArray139[i_29_ + 1][i_40_];
					int i_51_
					  = anIntArrayArray139[i_29_ + 1][i_40_ + 1];
					int i_52_ = anIntArrayArray139[i_29_][i_40_ + 1];
					int i_53_ = -1;
					int i_54_ = -1;
					if (i_43_ > 0) {
						int i_55_ = i_35_ * 256 / i_38_;
						int i_56_ = i_36_ / i_39_;
						int i_57_ = i_37_ / i_39_;
						i_53_ = method177 (i_55_, i_56_, i_57_);
						i_55_ = i_55_ + anInt123 & 0xff;
						i_57_ += anInt133;
						if (i_57_ < 0)
							i_57_ = 0;
						else if (i_57_ > 255)
							i_57_ = 255;
						i_54_ = method177 (i_55_, i_56_, i_57_);
					}
					if (i_10_ > 0) {
						boolean bool = true;
						if (i_43_ == 0 && (floorshadingtypes[i_10_][i_29_][i_40_]) != 0)
							bool = false;
						if (i_44_ > 0 && ! (FloorDefinition.definitions[i_44_ - 1].aBoolean393))
							bool = false;
						if (bool && i_45_ == i_46_ && i_45_ == i_47_ && i_45_ == i_48_)
							tilesettings2[i_10_][i_29_][i_40_] |= 0x924;
					}
					int i_58_ = 0;
					if (i_53_ != -1)
					  i_58_ = (TriangleRasterizer.shading$[method187 (i_54_, 96)]);
					/* Shading with no floor */
					if (i_44_ == 0)
						class25.method279 (i_10_, i_29_, i_40_, 0, 0, -1, i_45_, i_46_, i_47_, i_48_, method187 (i_53_, i_49_), method187 (i_53_, i_50_), method187 (i_53_, i_51_), method187 (i_53_, i_52_), 0, 0, 0, 0, i_58_, 0);
					/* Other shading...*/
					else {
						int shadingtype = ((floorshadingtypes[i_10_][i_29_][i_40_]) + 1);
						byte i_60_ = (floortexturerotation[i_10_][i_29_][i_40_]);
						FloorDefinition class22 = FloorDefinition.definitions[i_44_ - 1];
						int _txt = class22.textureId;
						int i_62_;
						int i_63_;
						if (_txt >= 0) {
							i_62_ = TriangleRasterizer.getAverageTextureColor (_txt, 12660);
							i_63_ = -1;
						/* 0xFF00FF - Purple */
						} else if (class22.anInt390 == 16711935) {
							i_62_ = 0;
							i_63_ = -2;
							_txt = -1;
						} else {
							i_63_ = method177 (class22.cArc, class22.iArc, class22.mChannel);
							i_62_ = (TriangleRasterizer.shading$[method185 (class22.anInt399, 96)]);
						}
						class25.method279 (i_10_, i_29_, i_40_, shadingtype, i_60_, _txt, i_45_, i_46_, i_47_, i_48_, method187 (i_53_, i_49_), method187 (i_53_, i_50_), method187 (i_53_, i_51_), method187 (i_53_, i_52_), method185 (i_63_, i_49_), method185 (i_63_, i_50_), method185 (i_63_, i_51_), method185 (i_63_, i_52_), i_58_, i_62_);
						}
						}
					}
				}
			}
		}
		for (int i_64_ = 1; i_64_ < ml_sizey - 1; i_64_++) {
			for (int i_65_ = 1; i_65_ < ml_sizex - 1; i_65_++)
				class25.method278 (i_10_, i_65_, i_64_, getHeightFromTilesettings (i_64_, i_10_, i_65_, 0));
		}
      }
    class25.method305 (-10, (byte) 3, 64, -50, 768, -50);
    for (int i_66_ = 0; i_66_ < ml_sizex; i_66_++)
      {
	for (int i_67_ = 0; i_67_ < ml_sizey; i_67_++)
	  {
	    if ((tileFlags[1][i_66_][i_67_] & 0x2) == 2)
	      class25.method276 (i_67_, i_66_, -438);
	  }
      }
				int i_68_ = 1;
				int i_69_ = 2;
				int i_70_ = 4;
				for (int i_71_ = 0; i_71_ < 4; i_71_++) {
					if (i_71_ > 0) {
						i_68_ <<= 3;
						i_69_ <<= 3;
						i_70_ <<= 3;
					  }
				for (int i_72_ = 0; i_72_ <= i_71_; i_72_++) {
					for (int i_73_ = 0; i_73_ <= ml_sizey; i_73_++) {
						for (int i_74_ = 0; i_74_ <= ml_sizex; i_74_++) {
							if ((tilesettings2[i_72_][i_74_][i_73_] & i_68_) != 0) {
								int i_75_ = i_73_;
								int i_76_ = i_73_;
								int i_77_ = i_72_;
								int i_78_ = i_72_;
								for (/**/; i_75_ > 0; i_75_--) {
									if (((tilesettings2[i_72_][i_74_][i_75_ - 1]) & i_68_) == 0)
										break;
								}
								for (/**/; i_76_ < ml_sizey; i_76_++) {
									if (((tilesettings2[i_72_][i_74_][i_76_ + 1]) & i_68_) == 0)
										break;
								}
								while_0_:
								for (/**/; i_77_ > 0; i_77_--)
								  {
									for (int i_79_ = i_75_; i_79_ <= i_76_; i_79_++)
									  {
									if (((tilesettings2[i_77_ - 1][i_74_]
										  [i_79_])
										 & i_68_)
										== 0)
									  break while_0_;
									  }
								  }
								  while_1_:
								for (/**/; i_78_ < i_71_; i_78_++)
								  {
									for (int i_80_ = i_75_; i_80_ <= i_76_; i_80_++)
									  {
									if (((tilesettings2[i_78_ + 1][i_74_]
										  [i_80_])
										 & i_68_)
										== 0)
									  break while_1_;
									  }
								  }
								int i_81_ = (i_78_ + 1 - i_77_) * (i_76_ - i_75_ + 1);
								if (i_81_ >= 8)
								  {
									int i_82_ = 240;
									int i_83_ = (heightmap[i_78_][i_74_][i_75_] - i_82_);
									int i_84_ = heightmap[i_77_][i_74_][i_75_];
									Palette.method277(i_71_, i_74_ * 128, i_84_, i_74_ * 128, i_76_ * 128 + 128, i_83_, anInt150, i_75_ * 128, 1);
									for (int i_85_ = i_77_; i_85_ <= i_78_; i_85_++)
									  {
									for (int i_86_ = i_75_; i_86_ <= i_76_;
										 i_86_++)
									  tilesettings2[i_85_][i_74_][i_86_]
										&= i_68_ ^ 0xffffffff;
									  }
								  }
							}
						if ((tilesettings2[i_72_][i_74_][i_73_] & i_69_) != 0) {
							int i_87_ = i_74_;
							int i_88_ = i_74_;
							int i_89_ = i_72_;
							int i_90_ = i_72_;
							for (/**/; i_87_ > 0; i_87_--) {
								if (((tilesettings2[i_72_][i_87_ - 1][i_73_])& i_69_) == 0)
									break;
							}
							for (/**/; i_88_ < ml_sizex; i_88_++) {
								if (((tilesettings2[i_72_][i_88_ + 1][i_73_]) & i_69_) == 0)
									break;
							}
							while_2_:
							for (/**/; i_89_ > 0; i_89_--) {
								for (int i_91_ = i_87_; i_91_ <= i_88_; i_91_++) {
									if (((tilesettings2[i_89_ - 1][i_91_][i_73_]) & i_69_) == 0)
										break while_2_;
								}
							}
							while_3_:
							for (/**/; i_90_ < i_71_; i_90_++) {
								for (int i_92_ = i_87_; i_92_ <= i_88_; i_92_++) {
									if (((tilesettings2[i_90_ + 1][i_92_][i_73_]) & i_69_) == 0)
										break while_3_;
								}
							}
							int i_93_ = (i_90_ + 1 - i_89_) * (i_88_ - i_87_ + 1);
							if (i_93_ >= 8) {
								int i_94_ = 240;
								int i_95_ = (heightmap[i_90_][i_87_][i_73_] - i_94_);
								int i_96_ = heightmap[i_89_][i_87_][i_73_];
								Palette.method277 (i_71_, i_87_ * 128, i_96_, i_88_ * 128 + 128, i_73_ * 128, i_95_, anInt150, i_73_ * 128, 2);
								for (int i_97_ = i_89_; i_97_ <= i_90_; i_97_++) {
									for (int i_98_ = i_87_; i_98_ <= i_88_; i_98_++)
										tilesettings2[i_97_][i_98_][i_73_] &= i_69_ ^ 0xffffffff;
								}
							}
						}
						if((tilesettings2[i_72_][i_74_][i_73_] & i_70_) != 0) {
							int i_99_ = i_74_;
							int i_100_ = i_74_;
							int i_101_ = i_73_;
							int i_102_ = i_73_;
							for (/**/; i_101_ > 0; i_101_--) {
								if (((tilesettings2[i_72_][i_74_][i_101_ - 1]) & i_70_) == 0)
									break;
							}
							for (/**/; i_102_ < ml_sizey; i_102_++) {
								if (((tilesettings2[i_72_][i_74_][i_102_ + 1]) & i_70_) == 0)
									break;
							}
							while_4_:
							for (/**/; i_99_ > 0; i_99_--) {
								for (int i_103_ = i_101_; i_103_ <= i_102_; i_103_++) {
									if (((tilesettings2[i_72_][i_99_ - 1][i_103_]) & i_70_) == 0)
										break while_4_;
								}
							}
							while_5_:
							for (/**/; i_100_ < ml_sizex; i_100_++) {
								for (int i_104_ = i_101_; i_104_ <= i_102_; i_104_++) {
									if (((tilesettings2[i_72_][i_100_ + 1][i_104_]) & i_70_) == 0)
										break while_5_;
								}
							}
							if ((i_100_ - i_99_ + 1) * (i_102_ - i_101_ + 1) >= 4) {
								int i_105_ = heightmap[i_72_][i_99_][i_101_];
								Palette.method277 (i_71_, i_99_ * 128, i_105_, i_100_ * 128 + 128, i_102_ * 128 + 128, i_105_, anInt150, i_101_ * 128, 4);
								for (int i_106_ = i_99_; i_106_ <= i_100_; i_106_++) {
									for (int i_107_ = i_101_; i_107_ <= i_102_; i_107_++)
										tilesettings2[i_72_][i_106_][i_107_] &= i_70_ ^ 0xffffffff;
								}
							}
						}
					}
				}
			}
		}
	}
  
	public static int generatePerlinNoise(int i, int i_108_) {
		int i_109_ = (interpolatedNoise (i + 45365, i_108_ + 91923, 4) - 128 + (interpolatedNoise (i + 10294, i_108_ + 37821, 2) - 128 >> 1) + (interpolatedNoise (i, i_108_, 1) - 128 >> 2));
		i_109_ = (int) ((double) i_109_ * 0.3) + 35;
		if (i_109_ < 10)
			i_109_ = 10;
		else if (i_109_ > 60)
			i_109_ = 60;
		return i_109_;
	}
  
	public static void loadExtraObjects(byte i, ByteBuffer buffer0, OndemandHandler class42_sub1) {
		int i_110_ = -1;
		if (i != -107)
			aBoolean141 = ! aBoolean141;
		for (;;) {
			int i_111_ = buffer0.getSmartB ();
			if (i_111_ == 0)
				break;
			i_110_ += i_111_;
			ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (i_110_);
			class46.method574(class42_sub1, -235);
			for (;;) {
				int i_112_ = buffer0.getSmartB ();
				if (i_112_ == 0)
				  break;
				buffer0.getUbyte ();
			}
		}
	}
  
	public void method174(int i, int i_113_, int junk, int i_115_, int i_116_) {
		for (int i_117_ = i; i_117_ <= i + i_113_; i_117_++) {
			for (int i_118_ = i_116_; i_118_ <= i_116_ + i_115_; i_118_++) {
				if (i_118_ >= 0 && i_118_ < ml_sizex && i_117_ >= 0 && i_117_ < ml_sizey) {
					shadowmap[0][i_118_][i_117_] = (byte) 127;
					if (i_118_ == i_116_ && i_118_ > 0)
						heightmap[0][i_118_][i_117_]  = heightmap[0][i_118_ - 1][i_117_];
					if (i_118_ == i_116_ + i_115_ && i_118_ < ml_sizex - 1)
						heightmap[0][i_118_][i_117_] = heightmap[0][i_118_ + 1][i_117_];
					if (i_117_ == i && i_117_ > 0)
						heightmap[0][i_118_][i_117_] = heightmap[0][i_118_][i_117_ - 1];
					if (i_117_ == i + i_113_ && i_117_ < ml_sizey - 1)
						heightmap[0][i_118_][i_117_] = heightmap[0][i_118_][i_117_ + 1];
				}
			}
		}
  }
  
	public void spawnObject(int spawny, Palette palette, PlaneFlags collisionmap, int objtype, int spwnz, int spwnx, int objid, boolean junk, int objrotation) {
		if (! lowmemory || (tileFlags[0][spwnx][spawny] & 0x2) != 0 || ((tileFlags[spwnz][spwnx][spawny] & 0x10) == 0 && getHeightFromTilesettings (spawny, spwnz, spwnx, 0) == ml_hieght)) {
		if (spwnz < anInt145)
		  anInt145 = spwnz;
		/**
		 * xx
		 * *x
		 **/
		int height_00 = heightmap[spwnz][spwnx][spawny];
		int height_10 = heightmap[spwnz][spwnx + 1][spawny];
		int height_11 = heightmap[spwnz][spwnx + 1][spawny + 1];
		int height_01 = heightmap[spwnz][spwnx][spawny + 1];
		/* Average Height */
		int avrheight = height_00 + height_10 + height_11 + height_01 >> 2;
		ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (objid);
		/* 1073741824 = 31st bit */
		int objhash = spwnx + (spawny << 7) + (objid << 14) + 1073741824;
		/* -2147483648 = 32nd bit */
		if (! class46.aBoolean778)
		  objhash += -2147483648;
		byte infohash = (byte) ((objrotation << 6) + objtype);
			if (!junk) {
			if (objtype == 22) {
				if (! lowmemory || class46.aBoolean778 || class46.aBoolean736) {
					Entity class30_sub2_sub4;
					if (class46.anInt781 == -1 && class46.anIntArray759 == null)
						class30_sub2_sub4 = class46.method578(22, objrotation, height_00, height_10, height_11, height_01, -1);
					else
						class30_sub2_sub4 = new GameObject(objid, objrotation, 22, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
					palette.method280 (spwnz, avrheight, spawny, 68, class30_sub2_sub4, infohash, objhash, spwnx);
					if (class46.aBoolean767 && class46.aBoolean778 && collisionmap != null)
						collisionmap.method213 (spawny, spwnx);
				}
			} else if (objtype == 10 || objtype == 11) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
				  class30_sub2_sub4 = class46.method578 (10, objrotation, height_00, height_10, height_11, height_01, -1);
				else
				  class30_sub2_sub4 = new GameObject (objid, objrotation, 10, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
				if (class30_sub2_sub4 != null) {
					int i_131_ = 0;
					if (objtype == 11)
					  i_131_ += 256;
					int i_132_;
					int i_133_;
					if (objrotation == 1 || objrotation == 3) {
						i_132_ = class46.size;
						i_133_ = class46.anInt744;
					} else {
						i_132_ = class46.anInt744;
						i_133_ = class46.size;
					}
					if (palette.method284 (objhash, infohash, avrheight, i_133_, class30_sub2_sub4, i_132_, spwnz, i_131_, (byte) 110, spawny, spwnx) && class46.aBoolean779) {
						Model class30_sub2_sub4_sub6;
						if (class30_sub2_sub4 instanceof Model)
						  class30_sub2_sub4_sub6 = (Model) class30_sub2_sub4;
						else
							class30_sub2_sub4_sub6 = class46.method578 (10, objrotation, height_00, height_10, height_11, height_01, -1);
						if (class30_sub2_sub4_sub6 != null) {
							for (int i_134_ = 0; i_134_ <= i_132_; i_134_++) {
								for (int i_135_ = 0; i_135_ <= i_133_; i_135_++) {
									int i_136_ = class30_sub2_sub4_sub6.maxdistxz / 4;
									if (i_136_ > 30)
									  i_136_ = 30;
									if (i_136_ > (shadowmap[spwnz][spwnx + i_134_][spawny + i_135_])) 
										shadowmap[spwnz][spwnx + i_134_][spawny + i_135_] = (byte) i_136_;
								}
							}
						}
					}
				}
				if (class46.aBoolean767 && collisionmap != null)
					collisionmap.method212 (class46.aBoolean757, anInt138, class46.anInt744, class46.size, spwnx, spawny, objrotation);
			} else if (objtype >= 12) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
					class30_sub2_sub4 = class46.method578 (objtype, objrotation, height_00, height_10, height_11, height_01, -1);
				else
					class30_sub2_sub4 = new GameObject (objid, objrotation, objtype, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
				palette.method284 (objhash, infohash, avrheight, 1, class30_sub2_sub4, 1, spwnz, 0, (byte) 110, spawny, spwnx);
				if (objtype >= 12 && objtype <= 17 && objtype != 13 && spwnz > 0)
					tilesettings2[spwnz][spwnx][spawny] |= 0x924;
				if (class46.aBoolean767 && collisionmap != null)
				  collisionmap.method212 (class46.aBoolean757, anInt138, class46.anInt744, class46.size, spwnx, spawny, objrotation);
			} else if (objtype == 0) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
					class30_sub2_sub4 = class46.method578 (0, objrotation, height_00, height_10, height_11, height_01, -1);
				else
					class30_sub2_sub4 = new GameObject (objid, objrotation, 0, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
				palette.putWall (anIntArray152[objrotation], class30_sub2_sub4, true, objhash, spawny, infohash, spwnx, null, avrheight, 0, spwnz);
				if (objrotation == 0) {
					if (class46.aBoolean779) {
						shadowmap[spwnz][spwnx][spawny] = (byte) 50;
						shadowmap[spwnz][spwnx][spawny + 1] = (byte) 50;
					}
					if (class46.aBoolean764)
						tilesettings2[spwnz][spwnx][spawny] |= 0x249;
				} else if (objrotation == 1) {
					if (class46.aBoolean779) {
						shadowmap[spwnz][spwnx][spawny + 1] = (byte) 50;
						shadowmap[spwnz][spwnx + 1][spawny + 1] = (byte) 50;
					}
					if (class46.aBoolean764)
						tilesettings2[spwnz][spwnx][spawny + 1] |= 0x492;
				} else if (objrotation == 2) {
					if (class46.aBoolean779) {
						shadowmap[spwnz][spwnx + 1][spawny] = (byte) 50;
						shadowmap[spwnz][spwnx + 1][spawny + 1] = (byte) 50;
					}
					if (class46.aBoolean764)
					  tilesettings2[spwnz][spwnx + 1][spawny] |= 0x249;
				} else if (objrotation == 3) {
					if (class46.aBoolean779) {
						shadowmap[spwnz][spwnx][spawny] = (byte) 50;
						shadowmap[spwnz][spwnx + 1][spawny] = (byte) 50;
					}
					if (class46.aBoolean764)
					  tilesettings2[spwnz][spwnx][spawny] |= 0x492;
				}
				if (class46.aBoolean767 && collisionmap != null)
					collisionmap.method211 (spawny, objrotation, spwnx, objtype, class46.aBoolean757);
				if (class46.anInt775 != 16)
					palette.method290 (spawny, 441, class46.anInt775, spwnx, spwnz);
			} else if (objtype == 1) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
				  class30_sub2_sub4
					= class46.method578 (1, objrotation, height_00, height_10, height_11,
							 height_01, -1);
				else
				  class30_sub2_sub4 = new GameObject (objid, objrotation, 1, height_10, (byte) 7, height_11, height_00,
								  height_01, class46.anInt781,
								  true);
				palette.putWall (anIntArray140[objrotation], class30_sub2_sub4, true, objhash, spawny, infohash, spwnx, null, avrheight, 0, spwnz);
				if (class46.aBoolean779) {
					if (objrotation == 0)
						shadowmap[spwnz][spwnx][spawny + 1] = (byte) 50;
					else if (objrotation == 1)
						shadowmap[spwnz][spwnx + 1][spawny + 1] = (byte) 50;
					else if (objrotation == 2)
						shadowmap[spwnz][spwnx + 1][spawny] = (byte) 50;
					else if (objrotation == 3)
						shadowmap[spwnz][spwnx][spawny] = (byte) 50;
				}
				if (class46.aBoolean767 && collisionmap != null)
					collisionmap.method211 (spawny, objrotation, spwnx, objtype, class46.aBoolean757);
			} else if (objtype == 2) {
				int i_137_ = objrotation + 1 & 0x3;
				Entity class30_sub2_sub4;
				Entity class30_sub2_sub4_138_;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
				  {
					class30_sub2_sub4
					  = class46.method578 (2, 4 + objrotation, height_00, height_10,
							   height_11, height_01, -1);
					class30_sub2_sub4_138_
					  = class46.method578 (2, i_137_, height_00, height_10, height_11,
							   height_01, -1);
				  }
				else
				  {
					class30_sub2_sub4
					  = new GameObject (objid, 4 + objrotation, 2,
									height_10, (byte) 7, height_11,
									height_00, height_01,
									class46.anInt781, true);
					class30_sub2_sub4_138_
					  = new GameObject (objid, i_137_, 2, height_10,
									(byte) 7, height_11, height_00,
									height_01, class46.anInt781,
									true);
				  }
				palette.putWall (anIntArray152[objrotation], class30_sub2_sub4, true, objhash, spawny, infohash, spwnx, class30_sub2_sub4_138_, avrheight, anIntArray152[i_137_], spwnz);
				if (class46.aBoolean764) {
					if (objrotation == 0) {
						tilesettings2[spwnz][spwnx][spawny] |= 0x249;
						tilesettings2[spwnz][spwnx][spawny + 1] |= 0x492;
					} else if (objrotation == 1) {
						tilesettings2[spwnz][spwnx][spawny + 1] |= 0x492;
						tilesettings2[spwnz][spwnx + 1][spawny] |= 0x249;
					} else if (objrotation == 2) {
						tilesettings2[spwnz][spwnx + 1][spawny] |= 0x249;
						tilesettings2[spwnz][spwnx][spawny] |= 0x492;
					} else if (objrotation == 3) {
						tilesettings2[spwnz][spwnx][spawny] |= 0x492;
						tilesettings2[spwnz][spwnx][spawny] |= 0x249;
					}
				}
				if (class46.aBoolean767 && collisionmap != null)
				  collisionmap.method211 (spawny, objrotation, spwnx, objtype,
							 class46.aBoolean757);
				if (class46.anInt775 != 16)
				  palette.method290 (spawny, 441, class46.anInt775, spwnx, spwnz);
			} else if (objtype == 3) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
				  class30_sub2_sub4
					= class46.method578 (3, objrotation, height_00, height_10, height_11,
							 height_01, -1);
				else
				  class30_sub2_sub4
					= new GameObject (objid, objrotation, 3, height_10,
								  (byte) 7, height_11, height_00,
								  height_01, class46.anInt781,
								  true);
				palette.putWall (anIntArray140[objrotation], class30_sub2_sub4,
						   true, objhash, spawny, infohash, spwnx, null,
						   avrheight, 0, spwnz);
				if (class46.aBoolean779)
				  {
					if (objrotation == 0)
					  shadowmap[spwnz][spwnx][spawny + 1]
					= (byte) 50;
					else if (objrotation == 1)
					  shadowmap[spwnz][spwnx + 1][spawny + 1]
					= (byte) 50;
					else if (objrotation == 2)
					  shadowmap[spwnz][spwnx + 1][spawny]
					= (byte) 50;
					else if (objrotation == 3)
					  shadowmap[spwnz][spwnx][spawny] = (byte) 50;
				  }
				if (class46.aBoolean767 && collisionmap != null)
				  collisionmap.method211 (spawny, objrotation, spwnx, objtype,
							 class46.aBoolean757);
			} else if (objtype == 9) {
				Entity class30_sub2_sub4;
				if (class46.anInt781 == -1 && class46.anIntArray759 == null)
				  class30_sub2_sub4
					= class46.method578 (objtype, objrotation, height_00, height_10,
							 height_11, height_01, -1);
				else
				  class30_sub2_sub4
					= new GameObject (objid, objrotation, objtype,
								  height_10, (byte) 7, height_11,
								  height_00, height_01,
								  class46.anInt781, true);
				palette.method284 (objhash, infohash, avrheight, 1,
						   class30_sub2_sub4, 1, spwnz, 0, (byte) 110,
						   spawny, spwnx);
				if (class46.aBoolean767 && collisionmap != null)
				  collisionmap.method212 (class46.aBoolean757, anInt138,
							 class46.anInt744, class46.size,
							 spwnx, spawny, objrotation);
			} else {
					if (class46.aBoolean762) {
						if (objrotation == 1) {
							int i_139_ = height_01;
							height_01 = height_11;
							height_11 = height_10;
							height_10 = height_00;
							height_00 = i_139_;
						} else if (objrotation == 2) {
							int i_140_ = height_01;
							height_01 = height_10;
							height_10 = i_140_;
							i_140_ = height_11;
							height_11 = height_00;
							height_00 = i_140_;
						} else if (objrotation == 3) {
							int i_141_ = height_01;
							height_01 = height_00;
							height_00 = height_10;
							height_10 = height_11;
							height_11 = i_141_;
						}
					}
					if (objtype == 4) {
						Entity class30_sub2_sub4;
						if (class46.anInt781 == -1 && class46.anIntArray759 == null)
							class30_sub2_sub4 = class46.method578 (4, 0, height_00, height_10, height_11, height_01, -1);
						else
							class30_sub2_sub4 = new GameObject (objid, 0, 4, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
						palette.putWallDecoration (objhash, spawny, objrotation * 512, -460, spwnz, 0, avrheight, class30_sub2_sub4, spwnx, infohash, 0, anIntArray152[objrotation]);
					} else if (objtype == 5) {
						int i_142_ = 16;
						int i_143_ = palette.method300 (spwnz, spwnx, spawny);
						if (i_143_ > 0)
						  i_142_ = ObjectDefinition.getObjectDefinition (i_143_ >> 14 & 0x7fff).anInt775;
						Entity class30_sub2_sub4;
						if (class46.anInt781 == -1 && class46.anIntArray759 == null)
						  class30_sub2_sub4 = class46.method578 (4, 0, height_00, height_10, height_11, height_01, -1);
						else
						  class30_sub2_sub4 = new GameObject (objid, 0, 4, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
						palette.putWallDecoration (objhash, spawny, objrotation * 512, -460, spwnz, anIntArray137[objrotation] * i_142_, avrheight, class30_sub2_sub4, spwnx, infohash, anIntArray144[objrotation] * i_142_, anIntArray152[objrotation]);
					} else if (objtype == 6) {
						Entity class30_sub2_sub4;
						if (class46.anInt781 == -1 && class46.anIntArray759 == null)
							class30_sub2_sub4 = class46.method578 (4, 0, height_00, height_10, height_11, height_01, -1);
						else
							class30_sub2_sub4 = new GameObject (objid, 0, 4, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
						palette.putWallDecoration (objhash, spawny, objrotation, -460, spwnz, 0, avrheight, class30_sub2_sub4, spwnx, infohash, 0, 256);
					} else if (objtype == 7) {
						Entity class30_sub2_sub4;
						if (class46.anInt781 == -1 && class46.anIntArray759 == null)
							class30_sub2_sub4 = class46.method578 (4, 0, height_00, height_10, height_11, height_01, -1);
						else
							class30_sub2_sub4 = new GameObject (objid, 0, 4, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
						palette.putWallDecoration (objhash, spawny, objrotation, -460, spwnz, 0, avrheight, class30_sub2_sub4, spwnx, infohash, 0, 512);
					} else if (objtype == 8) {
						Entity class30_sub2_sub4;
						if (class46.anInt781 == -1 && class46.anIntArray759 == null)
							class30_sub2_sub4 = class46.method578(4, 0, height_00, height_10, height_11, height_01, -1);
						else
							class30_sub2_sub4 = new GameObject(objid, 0, 4, height_10, (byte) 7, height_11, height_00, height_01, class46.anInt781, true);
						palette.putWallDecoration(objhash, spawny, objrotation, -460, spwnz, 0, avrheight, class30_sub2_sub4, spwnx, infohash, 0, 768);
					}
				}
			}
		}
	}
  
  public static int interpolatedNoise(int i, int i_144_, int i_145_) {
    int i_146_ = i / i_145_;
    int i_147_ = i & i_145_ - 1;
    int i_148_ = i_144_ / i_145_;
    int i_149_ = i_144_ & i_145_ - 1;
    int i_150_ = smoothNoise (i_146_, i_148_);
    int i_151_ = smoothNoise (i_146_ + 1, i_148_);
    int i_152_ = smoothNoise (i_146_, i_148_ + 1);
    int i_153_ = smoothNoise (i_146_ + 1, i_148_ + 1);
    int i_154_ = cosineInterpolate (i_150_, i_151_, i_147_, i_145_);
    int i_155_ = cosineInterpolate (i_152_, i_153_, i_147_, i_145_);
    return cosineInterpolate (i_154_, i_155_, i_149_, i_145_);
  }
  
  public int method177 (int i, int i_156_, int i_157_)
  {
    if (i_157_ > 179)
      i_156_ /= 2;
    if (i_157_ > 192)
      i_156_ /= 2;
    if (i_157_ > 217)
      i_156_ /= 2;
    if (i_157_ > 243)
      i_156_ /= 2;
	/* >> 2, >> 5, >> 1*/
    int i_158_ = (i / 4 << 10) + (i_156_ / 32 << 7) + i_157_ / 2;
    return i_158_;
  }
  
  public static boolean method178 (int i, int i_159_, int junk)
  {
    ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (i);
    if (i_159_ == 11)
      i_159_ = 10;
    if (i_159_ >= 5 && i_159_ <= 8)
      i_159_ = 4;
    return class46.method577 (i_159_, true);
  }
  
  public void method179 (int i, int i_162_, PlaneFlags[] class11s, int i_163_,
			 int i_164_, int i_165_, byte[] is, int i_166_,
			 int i_167_, int i_168_)
  {
    for (int i_169_ = 0; i_169_ < 8; i_169_++)
      {
	for (int i_170_ = 0; i_170_ < 8; i_170_++)
	  {
	    if (i_164_ + i_169_ > 0 && i_164_ + i_169_ < 103
		&& i_168_ + i_170_ > 0 && i_168_ + i_170_ < 103)
	      class11s[i_167_].flagBuffer[i_164_ + i_169_][(i_168_
								    + i_170_)]
		&= ~0x1000000;
	  }
      }
    if (i_163_ < 9 || i_163_ > 9)
      {
	for (int i_171_ = 1; i_171_ > 0; i_171_++)
	  {
	    /* empty */
	  }
      }
    ByteBuffer buffer0 = new ByteBuffer (is);
    for (int i_172_ = 0; i_172_ < 4; i_172_++)
      {
	for (int i_173_ = 0; i_173_ < 64; i_173_++)
	  {
	    for (int i_174_ = 0; i_174_ < 64; i_174_++)
	      {
		if (i_172_ == i && i_173_ >= i_165_ && i_173_ < i_165_ + 8 && i_174_ >= i_166_ && i_174_ < i_166_ + 8)
				loadTiles_ (i_168_ + MapUtils.method156 (i_174_ & 0x7, i_162_, -383, i_173_ & 0x7), 0, buffer0, i_164_ + MapUtils.method155 (i_162_, i_174_ & 0x7, i_173_ & 0x7, false),
			     i_167_, i_162_, 942, 0);
			else
		  loadTiles_ (-1, 0, buffer0, -1, 0, 0, 942, 0);
	      }
	  }
      }
  }
  
	public void method180(byte[] is, int i, int i_175_, int i_176_, int i_177_, byte junk, PlaneFlags[] class11s) {
		for (int i_179_ = 0; i_179_ < 4; i_179_++) {
			for (int i_180_ = 0; i_180_ < 64; i_180_++) {
				for (int i_181_ = 0; i_181_ < 64; i_181_++) {
					if (i_175_ + i_180_ > 0 && i_175_ + i_180_ < 103 && i + i_181_ > 0 && i + i_181_ < 103)
						class11s[i_179_].flagBuffer[i_175_ + i_180_][i + i_181_] &= ~0x1000000;
				}
			}
		}
		ByteBuffer buffer0 = new ByteBuffer (is);
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++)
					loadTiles_(y + i, i_177_, buffer0, x + i_175_, z, 0, 942, i_176_);
			}
		}
	}
  
	public void loadTiles_(int y, int i_185_, ByteBuffer buffer0, int x, int z, int i_188_, int junk, int i_190_) {
		if (x >= 0 && x < 104 && y >= 0 && y < 104) {
			tileFlags[z][x][y] = (byte) 0;
			for (;;) {
				int opcode = buffer0.getUbyte();
				/* Generate height map from perlin noise */
				if (opcode == 0) {
					if (z == 0)
						heightmap[0][x][y] = -generatePerlinNoise(932731 + x + i_190_, 556238 + y + i_185_) * 8;
					else {
						heightmap[z][x][y] = heightmap[z - 1][x][y] - 240;
						break;
					}
					break;
				}
				if (opcode == 1) {
					int i_192_ = buffer0.getUbyte ();
					if (i_192_ == 1)
						i_192_ = 0;
					if (z == 0)
						heightmap[0][x][y] = - i_192_ * 8;
					else {
						heightmap[z][x][y] = (heightmap[z - 1][x][y] - i_192_ * 8);
						break;
					}
					break;
				}
				if (opcode <= 49) {
					floormap[z][x][y] = buffer0.getByte();
					floorshadingtypes[z][x][y] = (byte) ((opcode - 2) / 4); 
					floortexturerotation[z][x][y] = (byte) (opcode - 2 + i_188_ & 0x3);
				} else if (opcode <= 81)
					tileFlags[z][x][y] = (byte) (opcode - 49);
				else
					floormap2[z][x][y] = (byte) (opcode - 81);
			}
      } else {
			for (;;) {
				int opcode = buffer0.getUbyte ();
				if (opcode == 0)
					break;
				if (opcode == 1) {
					buffer0.getUbyte ();
					break;
				}
				if (opcode <= 49)
					buffer0.getUbyte ();
			}
		}
	}
  
  public int getHeightFromTilesettings (int i, int z, int i_195_, int i_196_)
  {
    if (i_196_ != 0)
      return 2;
    if ((tileFlags[z][i_195_][i] & 0x8) != 0)
      return 0;
    if (z > 0 && (tileFlags[1][i_195_][i] & 0x2) != 0)
      return z - 1;
    return z;
  }
  
	public void loadChunk(PlaneFlags[] class11s, Palette palette, int z, int palettex, int regiony, boolean junk, int h, byte[] bytes, int regionx, int rotation, int palettey) {
		ByteBuffer buffer0 = new ByteBuffer (bytes);
		int objid = -1;
		for (;;) {
			int opcode0 = buffer0.getSmartB ();
			if (opcode0 == 0)
			  break;
			objid += opcode0;
			int objinfo = 0;
			for (;;) {
				int i_206_ = buffer0.getSmartB();
				if (i_206_ == 0)
				  break;
				objinfo += i_206_ - 1;
				int objregiony = objinfo & 0x3f;
				int objregionx = objinfo >> 6 & 0x3f;
				int objz = objinfo >> 12;
				int rthash = buffer0.getUbyte ();
				int objtype = rthash >> 2;
				int objrotation = rthash & 0x3;
				if (objz == z && objregionx >= regionx && objregionx < regionx + 8 && objregiony >= regiony && objregiony < regiony + 8) {
					ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (objid);
					int spwnx = palettex + MapUtils.method157(rotation, class46.size, objregionx & 0x7, (byte) 113, objregiony & 0x7, class46.anInt744);
					int spwny = palettey + MapUtils.method158(-433, objregiony & 0x7, class46.size, rotation, class46.anInt744, objregionx & 0x7);
					if (spwnx > 0 && spwny > 0 && spwnx < 103 && spwny < 103) {
						int i_215_ = objz;
						if ((tileFlags[1][spwnx][spwny] & 0x2) == 2)
							i_215_--;
						PlaneFlags collisionmap = null;
						if (i_215_ >= 0)
							collisionmap = class11s[i_215_];
						spawnObject(spwny, palette, collisionmap, objtype, h, spwnx, objid, false, objrotation + rotation & 0x3);
					}
				}
			}
		}
	}
  
  public static int cosineInterpolate(int a, int b, int piK, int ratio)
  {
    int i_219_ = (65536 - TriangleRasterizer.COSINE_TABLE[piK * 1024 / ratio] >> 1);
    return (a * (65536 - i_219_) >> 16) + (b * i_219_ >> 16);
  }
  
  public int method185 (int i, int i_220_)
  {
    if (i == -2)
      return 12345678;
    if (i == -1)
      {
	if (i_220_ < 0)
	  i_220_ = 0;
	else if (i_220_ > 127)
	  i_220_ = 127;
	i_220_ = 127 - i_220_;
	return i_220_;
      }
    i_220_ = i_220_ * (i & 0x7f) / 128;
    if (i_220_ < 2)
      i_220_ = 2;
    else if (i_220_ > 126)
      i_220_ = 126;
    return (i & 0xff80) + i_220_;
  }
  
  public static int smoothNoise(int i, int i_221_) {
    int corners  = (noise (i - 1, i_221_ - 1) + noise (i + 1, i_221_ - 1) + noise (i - 1, i_221_ + 1) + noise (i + 1, i_221_ + 1));
    int sides = (noise (i - 1, i_221_) + noise (i + 1, i_221_) + noise (i, i_221_ - 1) + noise (i, i_221_ + 1));
    int center = noise (i, i_221_);
    return corners / 16 + sides / 8 + center / 4;
  }
 
	public static int method187 (int i, int i_225_) {
		if (i == -1)
		  return 12345678;
		i_225_ = i_225_ * (i & 0x7f) / 128;
		if (i_225_ < 2)
		  i_225_ = 2;
		else if (i_225_ > 126)
		  i_225_ = 126;
		return (i & 0xff80) + i_225_;
	}
  
  public static void method188 (Palette class25, int i, int i_226_, int i_227_,
				int i_228_, PlaneFlags class11, int[][][] is,
				int i_229_, int i_230_, int i_231_,
				byte i_232_)
  {
    int i_233_ = is[i_228_][i_229_][i_226_];
    int i_234_ = is[i_228_][i_229_ + 1][i_226_];
    int i_235_ = is[i_228_][i_229_ + 1][i_226_ + 1];
    int i_236_ = is[i_228_][i_229_][i_226_ + 1];
    if (i_232_ != 93)
      anInt153 = -145;
    int i_237_ = i_233_ + i_234_ + i_235_ + i_236_ >> 2;
    ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (i_230_);
    int i_238_ = i_229_ + (i_226_ << 7) + (i_230_ << 14) + 1073741824;
    if (! class46.aBoolean778)
      i_238_ += -2147483648;
    byte i_239_ = (byte) ((i << 6) + i_227_);
    if (i_227_ == 22)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4
	    = class46.method578 (22, i, i_233_, i_234_, i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, 22, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.method280 (i_231_, i_237_, i_226_, 68, class30_sub2_sub4,
			   i_239_, i_238_, i_229_);
	if (class46.aBoolean767 && class46.aBoolean778)
	  class11.method213 (i_226_, i_229_);
      }
    else if (i_227_ == 10 || i_227_ == 11)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4
	    = class46.method578 (10, i, i_233_, i_234_, i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, 10, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	if (class30_sub2_sub4 != null)
	  {
	    int i_240_ = 0;
	    if (i_227_ == 11)
	      i_240_ += 256;
	    int i_241_;
	    int i_242_;
	    if (i == 1 || i == 3)
	      {
		i_241_ = class46.size;
		i_242_ = class46.anInt744;
	      }
	    else
	      {
		i_241_ = class46.anInt744;
		i_242_ = class46.size;
	      }
	    class25.method284 (i_238_, i_239_, i_237_, i_242_,
			       class30_sub2_sub4, i_241_, i_231_, i_240_,
			       (byte) 110, i_226_, i_229_);
	  }
	if (class46.aBoolean767)
	  class11.method212 (class46.aBoolean757, anInt138, class46.anInt744,
			     class46.size, i_229_, i_226_, i);
      }
    else if (i_227_ >= 12)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4 = class46.method578 (i_227_, i, i_233_, i_234_,
						 i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, i_227_, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.method284 (i_238_, i_239_, i_237_, 1, class30_sub2_sub4, 1,
			   i_231_, 0, (byte) 110, i_226_, i_229_);
	if (class46.aBoolean767)
	  class11.method212 (class46.aBoolean757, anInt138, class46.anInt744,
			     class46.size, i_229_, i_226_, i);
      }
    else if (i_227_ == 0)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4
	    = class46.method578 (0, i, i_233_, i_234_, i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, 0, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.putWall (anIntArray152[i], class30_sub2_sub4, true, i_238_,
			   i_226_, i_239_, i_229_, null, i_237_, 0, i_231_);
	if (class46.aBoolean767)
	  class11.method211 (i_226_, i, i_229_, i_227_,
			     class46.aBoolean757);
      }
    else if (i_227_ == 1)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4
	    = class46.method578 (1, i, i_233_, i_234_, i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, 1, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.putWall (anIntArray140[i], class30_sub2_sub4, true, i_238_,
			   i_226_, i_239_, i_229_, null, i_237_, 0, i_231_);
	if (class46.aBoolean767)
	  class11.method211 (i_226_, i, i_229_, i_227_,
			     class46.aBoolean757);
      }
    else if (i_227_ == 2)
      {
	int i_243_ = i + 1 & 0x3;
	Entity class30_sub2_sub4;
	Entity class30_sub2_sub4_244_;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  {
	    class30_sub2_sub4 = class46.method578 (2, 4 + i, i_233_, i_234_,
						   i_235_, i_236_, -1);
	    class30_sub2_sub4_244_
	      = class46.method578 (2, i_243_, i_233_, i_234_, i_235_, i_236_,
				   -1);
	  }
	else
	  {
	    class30_sub2_sub4
	      = new GameObject (i_230_, 4 + i, 2, i_234_, (byte) 7,
					    i_235_, i_233_, i_236_,
					    class46.anInt781, true);
	    class30_sub2_sub4_244_
	      = new GameObject (i_230_, i_243_, 2, i_234_,
					    (byte) 7, i_235_, i_233_, i_236_,
					    class46.anInt781, true);
	  }
	class25.putWall (anIntArray152[i], class30_sub2_sub4, true, i_238_,
			   i_226_, i_239_, i_229_, class30_sub2_sub4_244_,
			   i_237_, anIntArray152[i_243_], i_231_);
	if (class46.aBoolean767)
	  class11.method211 (i_226_, i, i_229_, i_227_,
			     class46.aBoolean757);
      }
    else if (i_227_ == 3)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4
	    = class46.method578 (3, i, i_233_, i_234_, i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, 3, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.putWall (anIntArray140[i], class30_sub2_sub4, true, i_238_,
			   i_226_, i_239_, i_229_, null, i_237_, 0, i_231_);
	if (class46.aBoolean767)
	  class11.method211 (i_226_, i, i_229_, i_227_,
			     class46.aBoolean757);
      }
    else if (i_227_ == 9)
      {
	Entity class30_sub2_sub4;
	if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	  class30_sub2_sub4 = class46.method578 (i_227_, i, i_233_, i_234_,
						 i_235_, i_236_, -1);
	else
	  class30_sub2_sub4
	    = new GameObject (i_230_, i, i_227_, i_234_, (byte) 7,
					  i_235_, i_233_, i_236_,
					  class46.anInt781, true);
	class25.method284 (i_238_, i_239_, i_237_, 1, class30_sub2_sub4, 1,
			   i_231_, 0, (byte) 110, i_226_, i_229_);
	if (class46.aBoolean767)
	  class11.method212 (class46.aBoolean757, anInt138, class46.anInt744,
			     class46.size, i_229_, i_226_, i);
      }
    else
      {
	if (class46.aBoolean762)
	  {
	    if (i == 1)
	      {
		int i_245_ = i_236_;
		i_236_ = i_235_;
		i_235_ = i_234_;
		i_234_ = i_233_;
		i_233_ = i_245_;
	      }
	    else if (i == 2)
	      {
		int i_246_ = i_236_;
		i_236_ = i_234_;
		i_234_ = i_246_;
		i_246_ = i_235_;
		i_235_ = i_233_;
		i_233_ = i_246_;
	      }
	    else if (i == 3)
	      {
		int i_247_ = i_236_;
		i_236_ = i_233_;
		i_233_ = i_234_;
		i_234_ = i_235_;
		i_235_ = i_247_;
	      }
	  }
	if (i_227_ == 4)
	  {
	    Entity class30_sub2_sub4;
	    if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	      class30_sub2_sub4 = class46.method578 (4, 0, i_233_, i_234_, i_235_, i_236_, -1);
	    else
	      class30_sub2_sub4 = new GameObject (i_230_, 0, 4, i_234_, (byte) 7, i_235_, i_233_, i_236_, class46.anInt781, true);
	    class25.putWallDecoration (i_238_, i_226_, i * 512, -460, i_231_, 0, i_237_, class30_sub2_sub4, i_229_, i_239_, 0, anIntArray152[i]);
	  } else if (i_227_ == 5){ 
		int i_248_ = 16;
	    int i_249_ = class25.method300 (i_231_, i_229_, i_226_);
	    if (i_249_ > 0)
	      i_248_ = ObjectDefinition.getObjectDefinition (i_249_ >> 14 & 0x7fff).anInt775;
	    Entity class30_sub2_sub4;
	    if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	      class30_sub2_sub4 = class46.method578 (4, 0, i_233_, i_234_, i_235_, i_236_, -1);
	    else
	      class30_sub2_sub4 = new GameObject (i_230_, 0, 4, i_234_, (byte) 7, i_235_, i_233_, i_236_, class46.anInt781, true);
	    class25.putWallDecoration (i_238_, i_226_, i * 512, -460, i_231_, anIntArray137[i] * i_248_, i_237_, class30_sub2_sub4, i_229_, i_239_, anIntArray144[i] * i_248_, anIntArray152[i]);
	  } else if (i_227_ == 6) {
	    Entity class30_sub2_sub4;
	    if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	      class30_sub2_sub4
		= class46.method578 (4, 0, i_233_, i_234_, i_235_, i_236_, -1);
	    else
	      class30_sub2_sub4
		= new GameObject (i_230_, 0, 4, i_234_, (byte) 7,
					      i_235_, i_233_, i_236_,
					      class46.anInt781, true);
	    class25.putWallDecoration (i_238_, i_226_, i, -460, i_231_, 0, i_237_,
			       class30_sub2_sub4, i_229_, i_239_, 0, 256);
	  }
	else if (i_227_ == 7)
	  {
	    Entity class30_sub2_sub4;
	    if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	      class30_sub2_sub4
		= class46.method578 (4, 0, i_233_, i_234_, i_235_, i_236_, -1);
	    else
	      class30_sub2_sub4
		= new GameObject (i_230_, 0, 4, i_234_, (byte) 7,
					      i_235_, i_233_, i_236_,
					      class46.anInt781, true);
	    class25.putWallDecoration (i_238_, i_226_, i, -460, i_231_, 0, i_237_,
			       class30_sub2_sub4, i_229_, i_239_, 0, 512);
	  }
	else if (i_227_ == 8)
	  {
	    Entity class30_sub2_sub4;
	    if (class46.anInt781 == -1 && class46.anIntArray759 == null)
	      class30_sub2_sub4
		= class46.method578 (4, 0, i_233_, i_234_, i_235_, i_236_, -1);
	    else
	      class30_sub2_sub4
		= new GameObject (i_230_, 0, 4, i_234_, (byte) 7,
					      i_235_, i_233_, i_236_,
					      class46.anInt781, true);
	    class25.putWallDecoration (i_238_, i_226_, i, -460, i_231_, 0, i_237_,
			       class30_sub2_sub4, i_229_, i_239_, 0, 768);
	  }
      }
  }
  
  public static boolean method189 (int i, byte[] is, int i_250_, int i_251_)
  {
    if (i_251_ < 6 || i_251_ > 6)
      throw new NullPointerException ();
    boolean bool = true;
    ByteBuffer buffer0 = new ByteBuffer (is);
    int i_252_ = -1;
    for (;;)
      {
	int i_253_ = buffer0.getSmartB ();
	if (i_253_ == 0)
	  break;
	i_252_ += i_253_;
	int i_254_ = 0;
	boolean bool_255_ = false;
	for (;;)
	  {
	    if (bool_255_)
	      {
		int i_256_ = buffer0.getSmartB ();
		if (i_256_ == 0)
		  break;
		buffer0.getUbyte ();
	      }
	    else
	      {
		int i_257_ = buffer0.getSmartB ();
		if (i_257_ == 0)
		  break;
		i_254_ += i_257_ - 1;
		int i_258_ = i_254_ & 0x3f;
		int i_259_ = i_254_ >> 6 & 0x3f;
		int i_260_ = buffer0.getUbyte () >> 2;
		int i_261_ = i_259_ + i;
		int i_262_ = i_258_ + i_250_;
		if (i_261_ > 0 && i_262_ > 0 && i_261_ < 103 && i_262_ < 103)
		  {
		    ObjectDefinition class46 = ObjectDefinition.getObjectDefinition (i_252_);
		    if (i_260_ != 22 || ! lowmemory || class46.aBoolean778
			|| class46.aBoolean736)
		      {
			bool &= class46.method579 (true);
			bool_255_ = true;
		      }
		  }
	      }
	  }
      }
    return bool;
  }
  
	public void method190(int xOffset, PlaneFlags[] class11s, int yOffset, int junk, Palette class25, byte[] is) {
			ByteBuffer buffer = new ByteBuffer (is);
			int i_265_ = -1;
			for (;;) {
				int counter = buffer.getSmartB ();
				if (counter == 0)
				  break;
				i_265_ += counter;
				int coordData = 0;
				for (;;) {
					int offset = buffer.getSmartB();
					if (offset == 0)
					  break;
					coordData += offset - 1;
					int originY = coordData & 0x3f;
					int originX = coordData >> 6 & 0x3f;
					int originZ = coordData >> 12;
					int settingFlags = buffer.getUbyte();
					int type = settingFlags >> 2;
					int rotation = settingFlags & 0x3;
					int x = originX + xOffset;
					int y = originY + yOffset;
					if (x > 0 && y > 0 && x < 103 && y < 103) {
						int z = originZ;
						if ((tileFlags[1][x][y] & 0x2) == 2)
							z--;
						PlaneFlags class11 = null;
						if (z >= 0)
                                                    class11 = class11s[z];
						spawnObject(y, class25, class11, type, originZ, x, i_265_, false, rotation);
					}
				}
			}
	}
}