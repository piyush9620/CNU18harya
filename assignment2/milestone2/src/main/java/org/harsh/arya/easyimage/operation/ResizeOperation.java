package org.harsh.arya.easyimage.operation;

import com.fasterxml.jackson.annotation.JsonProperty;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.harsh.arya.easyimage.utils.ImageUtils;

@Data
@ToString
public class ResizeOperation extends Operation{

    @JsonProperty("height") public Integer height;
    @JsonProperty("width") public Integer width;

    public void apply(ImageUtils imageUtils,String outPath){
        String filename = imageUtils.getLast(filePath,"/");
        String fileext = imageUtils.getLast(filename,"\\.");
        ImageProcessor ip = resizeImage(filePath,height,width);
        imageUtils.writeFile(ip.getBufferedImage(),fileext,filename,outPath);
    }

    private ImageProcessor resizeImage(String filePath,Integer height,Integer width){
        ImagePlus imp = IJ.openImage(filePath);
        ImageProcessor ip = imp.getProcessor();
        Pair<Integer,Integer> newParams = getSizeParams(ip.getHeight(),ip.getWidth(),height,width);
        ip = ip.resize(newParams.getValue(), newParams.getKey());
        return ip;
    }

    public  Pair<Integer,Integer> getSizeParams(int oHeight,int oWidth,Integer nHeight,Integer nWidth){
        if(nHeight == null){
            nHeight = (oHeight*nWidth)/oWidth;
        }else if(nWidth == null){
            nWidth = (nHeight*oWidth)/oHeight;
        }
        System.out.println(nHeight+"x"+nWidth);
        return new ImmutablePair<>(nHeight,nWidth);
    }

}
