package id.weplus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.weplus.model.request.Banner;

public class ResponseBeranda {
    private String code;
    private boolean status;
    private String message;
    public BerandaData data;

    @Override
    public String toString() {
        return "ResponseBeranda{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BerandaData getData() {
        return data;
    }

    public void setData(BerandaData data) {
        this.data = data;
    }

    public class BerandaData {
        private String barangfavorite;
        private String polis_active;
        private String produk_baru;
        private String banner_layout;
        public List<Banner> banner;
        //        public List<Baru> baru; // todo gk ada variable baru di response api home
        public List<NewProduct> new_product_ads;
        public List<ProductFavorite> product_favorite; // todo gak ada variable favorite di response api
        @SerializedName("register_agent_url")
        private String registerAgentUrl;
        @SerializedName("is_agent")
        private int isAgent;
        @SerializedName("claim_webview")
        private String claimWebview;

        public String getRegisterAgentUrl() {
            return registerAgentUrl;
        }

        public void setRegisterAgentUrl(String registerAgentUrl) {
            this.registerAgentUrl = registerAgentUrl;
        }

        public int getIsAgent() {
            return isAgent;
        }

        public void setIsAgent(int isAgent) {
            this.isAgent = isAgent;
        }

        public String getClaimWebview() {
            return claimWebview;
        }

        public void setClaimWebview(String claimWebview) {
            this.claimWebview = claimWebview;
        }

        // todo coba lebih teliti bikin modal, jangan ngasal, disesuaikan dgn response api nya

        @Override
        public String toString() {
            return "BerandaData{" +
                    "banner=" + banner +
                    "polis_active=" + polis_active +
                    "new_product_ads=" + new_product_ads +
                    ", product_favorite =" + product_favorite +
                    '}';
        }

        public String getBarangFavorite() {
            return barangfavorite;
        }

        public void setBarangfavorite(String barangfavorite) {
            this.barangfavorite = barangfavorite;
        }

        public String getProdukbaru(){
            return produk_baru;
        }

        public void setProduk_baru(){
            this.produk_baru = produk_baru;
        }

        public String getBanner_layout(){
            return banner_layout;
        }

        public void setBanner_layout(){
            this.banner_layout = banner_layout;
        }

        public List<Banner> getBanner() {
            return banner;
        }

        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        public String getPolis_active() {
            return polis_active;
        }

        public void setPolis_active(String polis_active) {
            this.polis_active = polis_active;
        }

        public List<NewProduct> getNew_product_ads() {
            return new_product_ads;
        }

        public void setNew_product_ads(List<NewProduct> new_product_ads) {
            this.new_product_ads = new_product_ads;
        }

        public List<ProductFavorite> getFavorite() {
            return product_favorite;
        }

        public void setFavorite(List<ProductFavorite> favorite) {
            this.product_favorite = favorite;
        }



        public class NewProduct {
            private int id;
            private String title;
            private String image;
            private String decs;
            private int category_id;
            private int partner_id;
            private ParameterNewProduct parameterNewProduct;

            @Override
            public String toString() {
                return "NewProduct{" +
                        "id=" + id +
                        "title=" + title +
                        "image=" + image +
                        "decs=" + decs +
                        "parameternewproduk=" + parameterNewProduct +
                        '}';
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(int partner_id) {
                this.partner_id = partner_id;
            }

            public int getId() {
                return id;
            }

            public void setId() {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle() {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage() {
                this.image = image;
            }

            public String getDecs() {
                return decs;
            }

            public void setDecs() {
                this.decs = decs;
            }

        }

        public class ParameterNewProduct {
            private int category_id;
            private int category_name;
            private int partner_id;
            private int partner_name;
            private int additional_param;

            @Override
            public String toString() {
                return "ParameterNewProduct{" +
                        ", category_id=" + category_id +
                        ", category_name=" + category_name +
                        ", partner_id=" + partner_id +
                        ", partner_name=" + partner_name +
                        ", additional_param=" + additional_param +
                        '}';
            }


            public int getCategory_id() {
                    return category_id;
                }

            public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

            public int getCategory_name() {
                    return category_name;
                }

            public void setCategory_name(int category_name) {
                    this.category_name = category_name;
                }

            public int getPartner_id() {
                    return partner_id;
                }

            public void setPartner_id(int partner_id) {
                    this.partner_id = partner_id;
                }

            public int getPartner_name() {
                    return partner_name;
                 }

            public void setPartner_name(int partner_name) {
                    this.partner_name = partner_name;
                }

            public int getAdditional_param() {
                    return additional_param;
                }

            public void setAdditional_param(int additional_param) {
                    this.additional_param = additional_param;
                }


        }

        public class ProductFavorite {
            private int id;
            private String name;
            private String image;
            private ParameterFavorite parameter;

            @Override
            public String toString() {
                return "Favorite{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", image='" + image + '\'' +
                        ", parameter=" + parameter +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String img_url) {
                this.image = img_url;
            }

            public ParameterFavorite getParameter() {
                return parameter;
            }

            public void setParameter(ParameterFavorite parameter) {
                this.parameter = parameter;
            }

            public class ParameterFavorite {
                private int category_id;
                private int category_name;
                private int partner_id;
                private int partner_name;
                private int additional_param;


                @Override
                public String toString() {
                    return "ParameterPromo{" +
                            ", category_id=" + category_id +
                            ", category_name=" + category_name +
                            ", partner_id=" + partner_id +
                            ", partner_name=" + partner_name +
                            ", additional_param=" + additional_param +
                            '}';
                }

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public int getCategory_name() {
                    return category_name;
                }

                public void setCategory_name(int category_name) {
                    this.category_name = category_name;
                }

                public int getPartner_id() {
                    return partner_id;
                }

                public void setPartner_id(int partner_id) {
                    this.partner_id = partner_id;
                }

                public int getPartner_name() {
                    return partner_name;
                }

                public void setPartner_name(int partner_name) {
                    this.partner_name = partner_name;
                }

                public int getAdditional_param() {
                    return additional_param;
                }

                public void setAdditional_param(int additional_param) {
                    this.additional_param = additional_param;
                }
            }
        }
    }
}

