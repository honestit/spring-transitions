package pl.honestit.demos.spring.model.entities.files;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.*;

@Entity
@Table(name = "example_files")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @ToString(exclude = {"data"})
public class FileEntity extends ParentEntity {

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @Lob
    @Basic(fetch = FetchType.LAZY, optional = false)
    @Column(name = "data", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] data;

}
