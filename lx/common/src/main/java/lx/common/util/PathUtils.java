package lx.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 路径格式化工具
 */
public abstract class PathUtils
{
    private static Map<String, String> filePathMap = new HashMap<String, String>();
    
    private static Map<String, String> urlPathMap = new HashMap<String, String>();
    
    private static Map<String, String> toPageMap = new HashMap<String, String>();
    
    private static Map<String, String> jsonPathMap = new HashMap<String, String>();
    
    static
    {
        int lower = 65;
        while (lower <= 90)
        {
            filePathMap.put(String.valueOf((char)lower), String.valueOf((char)lower));
            lower++;
        }
        
        int upper = 90;
        while (upper <= 122)
        {
            filePathMap.put(String.valueOf((char)upper), String.valueOf((char)upper));
            upper++;
        }
        
        int letter = 0;
        while (letter <= 9)
        {
            filePathMap.put(String.valueOf(letter), String.valueOf(letter));
            letter++;
        }
        
        filePathMap.put("-", "-");
        filePathMap.put(".", ".");
        filePathMap.put(":", ":");
        filePathMap.put("/", "/");
        filePathMap.put("\\", "\\");
        filePathMap.put("_", "_");
        
        urlPathMap.putAll(filePathMap);
        urlPathMap.put("`", "`");
        urlPathMap.put("~", "~");
        urlPathMap.put("!", "!");
        urlPathMap.put("@", "@");
        urlPathMap.put("#", "#");
        urlPathMap.put("$", "$");
        urlPathMap.put("%", "%");
        urlPathMap.put("^", "^");
        urlPathMap.put("&", "&");
        urlPathMap.put("*", "*");
        urlPathMap.put("(", "(");
        urlPathMap.put(")", ")");
        urlPathMap.put("-", "-");
        urlPathMap.put("_", "_");
        urlPathMap.put("=", "=");
        urlPathMap.put("+", "+");
        urlPathMap.put("[", "[");
        urlPathMap.put("{", "{");
        urlPathMap.put("]", "]");
        urlPathMap.put("}", "}");
        urlPathMap.put(";", ";");
        urlPathMap.put(":", ":");
        urlPathMap.put("'", "'");
        urlPathMap.put("\"", "\"");
        urlPathMap.put("|", "|");
        urlPathMap.put("\\", "\\");
        urlPathMap.put(",", ",");
        urlPathMap.put("<", "<");
        urlPathMap.put(".", ".");
        urlPathMap.put(">", ">");
        urlPathMap.put("/", "/");
        urlPathMap.put("?", "?");
        
        toPageMap.put("<", "&#60");
        toPageMap.put(">", "&#62");
        
        jsonPathMap.putAll(urlPathMap);
        jsonPathMap.put(" ", " ");
    }
    
    private static String checkPath(String path, Map<String, String> map)
    {
        StringBuffer temp = new StringBuffer();
        if (StringUtils.isNotEmpty(path))
        {
            for (int i = 0; i < path.length(); i++)
            {
                if (map.get(String.valueOf(path.charAt(i))) != null)
                {
                    temp.append(map.get(String.valueOf(path.charAt(i))));
                }
            }
        }
        
        return temp.toString();
    }
    
    /**
     * 对JSON格式的PATH进行格式化
     * 
     * @param path 要校验的Path
     * @return expire
     */
    public static String jsonPathFormat(String path)
    {
        path = checkPath(path, jsonPathMap);
        return path;
    }
}
