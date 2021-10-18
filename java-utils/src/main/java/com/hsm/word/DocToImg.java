package com.hsm.word;

import com.aspose.words.*;

public class DocToImg {
    public static void main(String[] args) throws Exception{
        String filePath = "D:\\backup\\page";


        Document doc = new Document("D:\\backup\\智能作业_test1235 2.docx");
        PageRange pageRange = new PageRange(0, doc.getPageCount() - 1);
        ImageSaveOptions imageSaveOptions = new ImageSaveOptions(SaveFormat.PNG);

        imageSaveOptions.setPageSet(new PageSet(pageRange));
        imageSaveOptions.setResolution(256);
//        imageSaveOptions.setPrettyFormat(true);
//        imageSaveOptions.setUseAntiAliasing(true);
        imageSaveOptions.setPageSavingCallback(new HandlePageSavingCallback(filePath));

        doc.save(filePath + ".png", imageSaveOptions);
    }
}
class HandlePageSavingCallback implements IPageSavingCallback {
    String fileSuffix;

    public HandlePageSavingCallback(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    @Override
    public void pageSaving(PageSavingArgs pageSavingArgs) throws Exception {
        pageSavingArgs.setPageFileName(String.format("%s_%s.png",fileSuffix,pageSavingArgs.getPageIndex()));
    }
}