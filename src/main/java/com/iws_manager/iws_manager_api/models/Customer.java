


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer")
public class Customer {

    @Id // customerid
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchid", nullable = false)
    private Integer branchid;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companytypeid", nullable = false)
    private Integer companytypeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryid", nullable = false)
    private Integer countryid;

    @Column(name = "customerno", nullable = false)
    private String customerno;

    @Column(name = "customername1", nullable = false)
    private String customername1;
    
    @Column(name = "customername2", nullable = false)
    private String customername2;

    @Column(name = "email1", nullable = false)
    private String email1;

    @Column(name = "email2", nullable = false)
    private String email2;

    @Column(name = "email3", nullable = false)
    private String email3;

    @Column(name = "email4", nullable = false)
    private String email4;

    @Column(name = "homepage", nullable = false)
    private String homepage;

    @Column(name = "hoursperweek", nullable = false)
    private Double hoursperweek;

    @Column(name = "maxhoursmonth", nullable = false)
    private Double maxhoursmonth;

    @Column(name = "maxhoursyear", nullable = false)
    private Double maxhoursyear;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateid", nullable = false)
    private Integer stateid;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "taxno", nullable = false)
    private String taxno;

    @Column(name = "taxoffice", nullable = false)
    private String taxoffice;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;


}