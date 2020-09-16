resource "aws_cognito_user_pool" "psonia_users" {
  name             = "psonia"
  alias_attributes = ["email"]
}


