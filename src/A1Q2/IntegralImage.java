package A1Q2;

/**
 * Represents an integer integral image, which allows the user to query the mean
 * value of an arbitrary rectangular subimage in O(1) time.  Uses O(n) memory,
 * where n is the number of pixels in the image.
 *
 * @author Anton Sitkovets
 */
public class IntegralImage {

    private final int[][] integralImage;
    private final int imageHeight; // height of image (first index)
    private final int imageWidth; // width of image (second index)

    /**
     * Constructs an integral image from the given input image.  
     *
     * @author jameselder
     * @param image The image represented
     * @throws InvalidImageException Thrown if input array is not rectangular
     */
    public IntegralImage(int[][] image) throws InvalidImageException {
        this.imageWidth = image.length;
        this.integralImage = new int[image.length][image[0].length];
        this.imageHeight = image[0].length;
        
        int q = 0;
        int w = 0;
        
        for ( int columns = 0; columns < image.length ; columns++){
        	try{
        		q = w + 1;
        		if(image[q].length == image[w].length){
        			w++;
        		}else{
        			throw new InvalidImageException("Input array is not rectangular!");
        		}
        	}catch(ArrayIndexOutOfBoundsException e){
        		
        	}
        }
        for(int i = 0; i < image.length; i++){
        	int left = i - 1;
        	for(int j = 0; j < image[0].length; j++){
        		
        		int top = j -1;
        		if((top < 0) && (left < 0)){
        			integralImage[0][0] = image[0][0];
        			continue;
        		}
        		if((top >= 0) && (left < 0)){
        			integralImage[i][j] = image[i][j] + integralImage[i][top];
        			continue;
        		}
        		if((top < 0) && (left >= 0)){
        			integralImage[i][j] = image[i][j] + integralImage[left][j];
        			continue;
        		}
        		integralImage[i][j] = image[i][j] + integralImage[left][j] + integralImage[i][top] - integralImage[left][top];
        	}
        }
    }

    /**
     * Returns the mean value of the rectangular sub-image specified by the
     * top, bottom, left and right parameters. The sub-image should include
     * pixels in rows top and bottom and columns left and right.  For example,
     * top = 1, bottom = 2, left = 1, right = 2 specifies a 2 x 2 sub-image starting
     * at (top, left) coordinate (1, 1).  
     *
     * @author jameselder
     * @param top top row of sub-image
     * @param bottom bottom row of sub-image
     * @param left left column of sub-image
     * @param right right column of sub-image
     * @return 
     * @throws BoundaryViolationException if image indices are out of range
     * @throws NullSubImageException if top > bottom or left > right
     */
    public double meanSubImage(int top, int bottom, int left, int right) throws BoundaryViolationException, NullSubImageException {
        if((top > bottom) || (left > right)){
        	throw new NullSubImageException();
        }
        else if((top < 0) || (bottom < 0) || (left < 0) || (right < 0)){
        	throw new BoundaryViolationException();
        }
        else if((bottom > this.imageHeight) || (left > this.imageWidth) || (top > this.imageHeight) || (right > this.imageWidth)){
        	throw new BoundaryViolationException();
        }else{
        	int left_out = left -1;
        	int top_out = top -1;
        	
        	//first column
        	if((left_out < 0) && (top >= 0)){
        		left_out = 0;
        		return (integralImage[right][bottom] - integralImage[right -1][bottom])/ ((bottom - top) * (right - left));
        	}//first row
        	else if ((top_out < 0) && (left_out >= 0)){
        		top_out = 0;
        		return (integralImage[right][bottom] - integralImage[right][top -1])/ ((bottom - top) * (right - left));
        	}//first element is top left corner
        	else if((top_out < 0) && (left_out < 0)){
        		top_out = 0;
        		left_out = 0;
        		return integralImage[left][bottom] / ((bottom +1) * (left +1));
        	}else{
        		double numerator = this.integralImage[right][bottom] - this.integralImage[left_out][bottom] - this.integralImage[right][top_out]
        				+ this.integralImage[left_out][top_out];
        		double denominator = ((bottom - top) + 1) * ((right - left) + 1);
        		return numerator/denominator;
        	}
        	
        }
        
    }
}