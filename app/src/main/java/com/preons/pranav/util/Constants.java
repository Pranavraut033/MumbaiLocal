package com.preons.pranav.util;


import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created on 21-03-17 at 20:16 by Pranav Raut.
 * For MumbaiLocal
 */

@SuppressWarnings("WeakerAccess")
public class Constants {
    public static final int PASS_LENGTH = 6;
    public static final String USER = "username";
    public static final String PASS = "password";
    public static final String FULL_NAME = "full_name";
    public static final String FIELD_ERR = "Field required";
    public static final String USER_ERR = "Username too short";
    public static final String PASS_ERR = "Password too short";
    public static final String ADMIN = "admin1234";
    public static final String REMEMBER = "rem";
    public static final int REGISTER = 0x4d2;
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String LINE = "line";
    public static final String LIST = "list";
    public static final String AMOUNT = "amount";
    public static final String PASSENGERS = "passengers";
    public static final String USER_NAME = "userTable";
    public static final String VERSION = "id";
    //dbHelper
    public static final String DATABASE_NAME = "mumbai_local.db";
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PHONE = "phone";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PASS = "pass";
    public static final String USER_COLUMN_BAL = "bal";
    //dbHelper END
    //dbHelper2
    public static final String DATABASE_NAME1 = "transactions.db";
    public static final String TRANS_TABLE_NAME = "tickets";
    public static final String TRANS_COLUMN_ID = "id";
    public static final String TRANS_COLUMN_STATION_FROM = "fromS";
    public static final String TRANS_COLUMN_STATION_TO = "toS";
    public static final String TRANS_COLUMN_STATION_FOR = "forS";
    public static final String TRANS_COLUMN_AMOUNT = "amount";
    public static final String TRANS_COLUMN_METHOD = "method";
    public static final String TRANS_COLUMN_PASSENGERS = "passenger";
    public static final String TRANS_COLUMN_DATE = "date";
    //dbHelper2 END
    public static final String[] WESTERN = new String[]{
            "<b>Churchgate</b>", "Marine Lines", "Charni Road", "Grant Road", "<b>Mumbai Central</b>",
            "Mahalakshmi", "Lower Parel", "Elphinstone", "<b>Dadar</b>", "Matanga Road", "Mahin Junction",
            "<b>Bandra</b>", "Khar Road", "Santacruz", "Vile Parle", "<b>Andheri</b>", "Jogeshwari",
            "Ram Mandir", "Goregoan", "Malad", "Kandivali", "<b>Borivali</b>", "Dahisar",
            "<b>Bhayander</b>", "Naigoan", "<b>Vasai Road</b>", "Nalla Sopara", "<b>Virar</b>",
            "Vaitarana", "Saphale", "Kelva Road", "Palghar", "Umroli Road",
            "Boisar", "Vangoan", "<b>Dahanu Road</b>",
    };
    public static final String[] CENTRAL = new String[]{
            "<b>CST</b>", "Masjid", "Sandhurst Road", "<b>Byculla</b>",
            "Chinchopokli", "Currey Road", "Parel", "<b>Dadar</b>", "Mantunga",
            "Sion", "<b>Kurla</b>", "Vidyaviher", "<b>Ghatkopar</b>", "Vikhroli",
            "Kanjur Marg", "Bhandup", "Nahur", "Mulund", "<b>Thane</b>", "Kalva",
            "Mumbra", "Diva Junction", "Kopar", " <b>Dombivali</b>", "Thakurli",
            "<b>Kalyan</b>", "Vithalwadi", "Ulhas Nagar", "<b>Ambernath</b>",
            "<b>Badlapur</b>", "Vangani", "Shelu", "Neral", "Bhivpuri Road",
            "<b>Karjat</b>", "Palasdhari", "Kelavi", "Dolavli", "Lowjee",
            "<b>Khopoli</b>", "Shahad", "Ambivili", "<b>Tiwala</b>", "Khadavli",
            "Vasind", "<b>Asangoan</b>", "Atgoan", "Khardi", "<b>Kasara</b>"
    };
    public static final String[] HARBOUR = new String[]{
            "<b>CST</b>", "Masjid", "Sandhurst Road", "Dackyard Road",
            "Reay Road", "Cotton Green", "Sewri", "<b>Vadala Road</b>",
            "Kings Circle", "Mahin Junction", "<b>Bandra</b>", "khar Road",
            "Santacruz", "Vile Parle", "<b>Andheri</b>", "GTB Nagar", "Chunabhatti",
            "<b>Kurla</b>", "Tilaknagar", "Chembur", "Govandi", "<b>Mankhurd</b>", "<b>Vashi</b>",
            "Sanpada", "Juinagar", "<b>Nerul</b>", "Seawood Darave", "<b>Belapur CBD</b>", "<b>Kharghar</b>",
            "Manasarvar", "khandeshwar", "<b>Panvel</b>"
    };
    public static final String IS_PLATFORM = "isTicket";
    public static final String STATION = "station";
    public static float dDime;
    @Nullable
    public static SharedPreferences preferences;
    @Nullable
    public static SharedPreferences.Editor editor;

    public static String reString(String s) {
        return s.replace("<b>", "").replace("</b>", "");
    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {
                String files[] = src.list();
                int filesLength = files.length;
                for (String file : files) {
                    String src1 = (new File(src, file).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);
                }
            } else {
                copyFiles(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFiles(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

}
