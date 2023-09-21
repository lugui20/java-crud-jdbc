
package lugui20.dominio;


import lombok.Value;
import lombok.Builder;

@Value
@Builder
public final class Producer {

    private final Integer id;
    private final String name;   
    
}
