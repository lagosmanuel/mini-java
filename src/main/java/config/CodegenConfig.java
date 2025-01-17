package main.java.config;

public class CodegenConfig {
    public static final String DATA = ".DATA";
    public static final String CODE = ".CODE";
    public static final String VT_FORMAT = "vt_%s";
    public static final String INDEX_SUFFIX = "_%d";
    public static final String CLASS_SEPARATOR = "@";
    public static final String FUNCTION_NAME_FORMAT = "%s" + CLASS_SEPARATOR + "%s";
    public static final String ATTRIBUTE_NAME_FORMAT = "$%s" + CLASS_SEPARATOR + "%s";
    public static final String MAIN_LABEL = "_MAIN";
    public static final String HEAP_INIT_LABEL = "simple_heap_init";
    public static final String MALLOC_LABEL = "simple_malloc";
    public static final String LABEL_DUPLICATED = "Label %s already exists";
    public static final String LINE_SEPARATOR = ";----------------------------------------\n";
    public static final String LINE_SPACE = "";
    public static final String LABEL = "%s:";
    public static final String OFFSET_THIS = "3";
    public static final String NULL_VALUE = "0";
    public static final String FALSE_VALUE = "0";
    public static final String TRUE_VALUE = "1";
    public static final int PARAM_OFFSET = Integer.parseInt(OFFSET_THIS);
    public static final String EPILOGUE_SUFFIX = "_epilogue";
    public static final String IF_END = "_if_end";
    public static final String IF_ELSE = "_if_else";
    public static final String WHILE_CONDITION = "_while_condition";
    public static final String WHILE_END = "_while_end";
    public static final String STRING_LABEL = "_string";
    public static final String CASE_LABEL = "_case";
    public static final String DEFAULT_LABEL = "_default";
    public static final String SWITCH_END = "_switch_end";
    public static final String SWITCH_BLOCK_END = "_switch_block_end";
    public static final String STRING_COMPARE_CONDITION = "_string_compare_condition";
    public static final String STRING_COMPARE_END = "_string_compare_end";
    public static final String FOREACH_END = "_foreach_end";
    public static final String FOREACH_BLOCK_END = "_foreach_block_end";
    public static final String FOREACH_CONDITION = "_foreach_condition";
    public static final String FOR_END = "_for_end";
    public static final String FOR_BLOCK_END = "_for_block_end";
    public static final String FOR_CONDITION = "_for_condition";
    public static final String AND_END = "_and_end";
    public static final String OR_END = "_or_end";
}
