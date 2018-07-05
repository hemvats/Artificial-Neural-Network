import edu.neuralnet.core.activation.ActivationFunction;
import edu.neuralnet.core.input.InputSummingFunction;

public class SinusoidActivationFunction implements ActivationFunction 
{

    public double calculateOutput(double summedInput) 
    {

        return Math.sin(summedInput);

    }        

}