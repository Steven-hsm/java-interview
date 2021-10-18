package com.hsm.word;

import com.aspose.words.*;

import java.awt.*;

public class DocToImg {
    public static void main(String[] args) throws Exception{
        String filePath = "D:\\backup\\png\\KEY_FRACTIONALMETRICS";


        Document doc = new Document("C:\\Users\\steven\\Downloads\\智能作业_test1234.docx");
        PageRange pageRange = new PageRange(0, doc.getPageCount() - 1);
        ImageSaveOptions imageSaveOptions = new ImageSaveOptions(SaveFormat.PNG);

        imageSaveOptions.setPageSet(new PageSet(pageRange));
        imageSaveOptions.setResolution(256);

        GraphicsQualityOptions qualityOptions = new GraphicsQualityOptions();
        //qualityOptions.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // SmoothingMode
        //qualityOptions.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // TextRenderingHint
        //qualityOptions.getRenderingHints().put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY); // CompositingMode
        //qualityOptions.getRenderingHints().put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // CompositingQuality
        //qualityOptions.getRenderingHints().put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // InterpolationMode
        qualityOptions.getRenderingHints().put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON); // StringFormat
        imageSaveOptions.setGraphicsQualityOptions(qualityOptions);
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