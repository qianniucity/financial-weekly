package city.qianniu.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedVectorStoreConfig {
    @Bean
    VectorStore vectorStore(EmbeddingClient embeddingClient) {
        // It's like you using H2 database for your application when making some demo
        // VectorStore is also the same case
        return new SimpleVectorStore(embeddingClient);
    }
}
