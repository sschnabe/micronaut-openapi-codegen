package codegen;

import java.util.List;
import java.util.Optional;

import codegen.model.ResponseMultiple200;
import codegen.model.ResponseMultiple300;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.exceptions.HttpStatusException;

@Controller
class ResponseController implements ResponseApi {

	static final Double DOUBLE = 1234D;
	static final String STRING = "string";
	static final List<String> ARRAY = List.of("a", "b");

	@Override
	public List<String> array() {
		return ARRAY;
	}

	@Override
	public HttpResponse<Object> header() {
		return HttpResponse.ok();
	}

	@Override
	public Optional<List<String>> arrayNotFound(Boolean found) {
		return found ? Optional.of(ARRAY) : Optional.empty();
	}

	@Override
	public Long defaultResponse(Boolean error) {
		if (error) {
			throw new HttpStatusException(HttpStatus.BAD_GATEWAY, (Object) "nope");
		}
		return 3L;
	}

	@Override
	public HttpResponse<ResponseMultiple200> multipleResponseCodes(Boolean redirect) {
		return redirect
				? HttpResponse.status(HttpStatus.MULTIPLE_CHOICES).body(new ResponseMultiple200().setFoo(STRING))
				: HttpResponse.ok(new ResponseMultiple200().setFoo(STRING));
	}

	@Override
	public HttpResponse<Object> multipleDataTypes(Boolean redirect) {
		return redirect
				? HttpResponse.status(HttpStatus.MULTIPLE_CHOICES).body(new ResponseMultiple300().setBar(DOUBLE))
				: HttpResponse.ok(new ResponseMultiple200().setFoo(STRING));
	}

	@Override
	public HttpResponse<Object> multipleDataTypesNotFound(Boolean redirect, Boolean found) {
		if (redirect) {
			return HttpResponse.status(HttpStatus.MULTIPLE_CHOICES).body(new ResponseMultiple300().setBar(DOUBLE));
		}
		return found ? HttpResponse.ok(new ResponseMultiple200().setFoo(STRING)) : HttpResponse.notFound();
	}

	@Override
	public Optional<String> optional(Boolean found) {
		return found ? Optional.of(STRING) : Optional.empty();
	}

	@Override
	public String single() {
		return STRING;
	}

	@Override
	public void voidNormal() {}

	@Override
	public void voidNotFound(Boolean found) {
		if (!found) {
			throw new HttpStatusException(HttpStatus.NOT_FOUND, null);
		}
	}
}
